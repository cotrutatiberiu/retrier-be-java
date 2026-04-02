# Java + Spring Boot — Fullstack Employability Roadmap

> **App concept:** A personal finance tracker where users manage accounts, track income/expenses, set budgets, and export reports.
> Each phase adds features to this one app — so you always have context for *why* you're building something, not just *what*.

---

## Phase 1 — Auth & Security foundations
**Status: Done**

### What the user can do
- Register an account with email + password
- Log in and receive an access token (15 min) + refresh token (7 days via HttpOnly cookie)
- Call a protected endpoint to verify they're authenticated
- Refresh their access token without logging in again

### Backend subjects covered
- `BCryptPasswordEncoder` — password hashing
- JWT generation + validation with separate signing keys per token type
- `OncePerRequestFilter` — custom filter in the Spring Security chain
- `SecurityFilterChain` — configuring which routes are public vs protected
- `SessionCreationPolicy.STATELESS` — why JWTs don't need server sessions
- CSRF protection with `CookieCsrfTokenRepository`
- `@ControllerAdvice` + `@ExceptionHandler` — global exception handling
- Custom exception hierarchy (`EmailUsedException`, `RefreshTokenExpiredException`)
- Java Records as DTOs (`UserRegisterRequest`, `LoginResponse`)
- Flyway versioned migrations — why you never let Hibernate manage your schema in production
- Docker + docker-compose for Postgres + Redis

---

## Phase 2 — Accounts — CRUD & proper REST
**Goal: Learn how to build a complete, correct REST resource**

### What the user can do
- Create an account (e.g. "Main Bank", "Cash Wallet", "Savings")
- List all their accounts
- Update an account's name or currency
- Archive an account (soft delete — never hard delete financial data)
- Get a single account by ID (404 if not found or not theirs)

### Backend subjects covered
- `ResponseEntity<T>` — returning proper HTTP status codes (201, 200, 204, 404)
- `@Valid` + Jakarta Bean Validation annotations (`@NotBlank`, `@NotNull`, `@Size`, `@Min`)
- Service interface + implementation pattern — why you always code to an interface
- Mapper pattern — converting between Entity ↔ DTO manually (no MapStruct yet, do it by hand first)
- Soft delete pattern — `archived` boolean instead of deleting rows
- Custom exceptions per case (`AccountNotFoundException`, `AccountNameTakenException`)
- `@ControllerAdvice` handling multiple exception types with correct HTTP status per type
- `SLF4J` logging — replace every `System.out.println` with `log.info()` / `log.error()` / `log.warn()`

### Quick note on logging
```java
// Never do this:
System.out.println("Creating account: " + name);

// Always do this:
private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
log.info("Creating account '{}' for userId={}", name, userId);
```
This gets flagged in every real PR review. Fix it once, habit formed forever.

---

## Phase 3 — Categories — Recursive tree structure
**Goal: Learn self-referencing relationships and recursive data modeling**

### What the user can do
- Create a top-level category (e.g. "Food", "Transport", "Income")
- Create a sub-category under any existing category (e.g. "Groceries" under "Food")
- List all categories in a flat list
- Get categories as a tree (parent → children → grandchildren)
- System prevents duplicate names within the same parent
- System prevents circular references (A → B → A)

### Backend subjects covered
- Self-referencing `@ManyToOne` / `@OneToMany` JPA relationship
- Partial unique indexes in PostgreSQL (`WHERE parent_category_id IS NULL` vs `WHERE parent_category_id IS NOT NULL`)
- Building a tree structure in Java — converting a flat list into a nested structure using a Map
- Cycle detection — traversing a chain of parent IDs and detecting if a loop would form
- Recursive data in DTOs — a `CategoryResponse` that contains `List<CategoryResponse> children`
- `@JsonInclude(JsonInclude.Include.NON_EMPTY)` — don't serialize empty children arrays

---

## Phase 4 — Transactions — Business logic + Concurrency + Many-to-many
**Goal: Learn @Transactional, optimistic locking, many-to-many relationships, and why they matter**

### What the user can do
- Record an income or expense transaction (amount, description, date, account, category)
- See their account balance update automatically after each transaction
- Edit a transaction — balance recalculates correctly (reverses old, applies new)
- Delete a transaction — balance is reversed
- List transactions with filters: date range, account, category, type (income/expense)
- Paginate results (page number + page size)
- Add one or more tags to a transaction (e.g. "business", "recurring", "tax-deductible")
- Remove a tag from a transaction
- Filter transactions by tag
- List all tags the user has created

### Backend subjects covered
- `@Transactional` — what it actually does (unit of work, rollback on exception)
- `@Transactional` propagation levels — `REQUIRED` vs `REQUIRES_NEW`
- `@Version` — optimistic locking to prevent lost updates on account balance
- Race condition simulation — write a test that fires two concurrent transactions and show how balance breaks without locking, then show it fixed
- `NUMERIC(14,2)` for money — why `float`/`double` are wrong for currency
- `Pageable` + `Page<T>` — Spring Data's built-in pagination
- `@Query` with JPQL for custom filtered queries
- The N+1 query problem — you will hit this when loading transactions with account + category. Learn to spot it in the SQL logs, then fix it with `@EntityGraph` or a JOIN FETCH

### N+1 explained
If you load 50 transactions and each one lazily fetches its `Account`, Hibernate fires 51 queries — 1 for the list, then 1 per row. Fix:
```java
@EntityGraph(attributePaths = {"account", "category"})
List<Transaction> findByUserId(long userId);
```
Being able to explain this unprompted in an interview is a strong signal.

### Many-to-many: Tags on transactions
A transaction can have many tags. A tag can belong to many transactions. This is the only true many-to-many in the app — and it's a two-step lesson.

**Step 1 — implement it the naive way with `@ManyToMany`:**
```java
// On Transaction entity
@ManyToMany
@JoinTable(
  name = "transaction_tags",
  joinColumns = @JoinColumn(name = "transaction_id"),
  inverseJoinColumns = @JoinColumn(name = "tag_id")
)
private List<Tag> tags;
```
This works. Hibernate manages the join table automatically. Ship it.

**Step 2 — hit the limitation intentionally.**
Try to add an `applied_at` timestamp to the relationship — when was this tag added to this transaction? You can't. Hibernate's auto-managed join table has no room for extra columns. You are now forced to create an explicit join entity:

```java
@Entity
@Table(name = "transaction_tags")
public class TransactionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Column(name = "applied_at", nullable = false)
    private Instant appliedAt;
}
```

Now `Transaction` and `Tag` each have `@OneToMany` to `TransactionTag` instead of `@ManyToMany` to each other. This is the pattern used in virtually every real production codebase — explicit join entity from day one, even if you don't need extra columns yet.

**What this teaches you:**
- `@ManyToMany` with `@JoinTable` — the naive approach and why it exists
- Why `@ManyToMany` breaks the moment you need data on the relationship
- Explicit join entity pattern — `@ManyToOne` on each side with its own `@Id`
- The refactor path — you'll feel the pain, which means you'll remember the lesson
- Cascade behaviour — what happens to `TransactionTag` rows when a `Transaction` is deleted (`CascadeType.ALL` + `orphanRemoval = true`)

**Schema:**
```sql
CREATE TABLE tags (
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    name       TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX uq_tags_user_name ON tags (user_id, LOWER(name));

CREATE TABLE transaction_tags (
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    transaction_id BIGINT      NOT NULL REFERENCES transactions (id) ON DELETE CASCADE,
    tag_id         BIGINT      NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    applied_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX uq_transaction_tag ON transaction_tags (transaction_id, tag_id);
```

---

## Phase 5 — Testing — Unit + Integration
**Goal: Learn to test Spring applications properly. This is the #1 employability gap.**

### What you test (no new user features — this phase covers the whole app so far)
- Every service method: happy path + error cases
- Every controller endpoint: correct status codes, correct response body, correct errors
- The full HTTP stack against a real database

### Backend subjects covered
- **JUnit 5** — `@Test`, `@BeforeEach`, `@ParameterizedTest`
- **Mockito** — `@Mock`, `@InjectMocks`, `when().thenReturn()`, `verify()`, `assertThrows()`
- **`@WebMvcTest`** — loads only the web layer (controller + security), mocks the service. Fast.
- **`MockMvc`** — perform fake HTTP requests and assert status + JSON body
- **`@SpringBootTest`** — loads the full application context. Use for integration tests.
- **Testcontainers** — spins up a real PostgreSQL container for tests. Modern standard.
- **`@Transactional` on tests** — rolls back DB state after each test automatically
- given / when / then naming structure — makes tests readable like documentation

### Structure to follow
```
unit/
  AccountServiceTest.java        ← Mockito, no Spring context
  TransactionServiceTest.java

integration/
  AccountControllerIT.java       ← @SpringBootTest + MockMvc + Testcontainers
  TransactionControllerIT.java
  AuthControllerIT.java
```

### What interviewers actually ask
- "How would you test a service that calls a repository?" → Mockito mock the repo
- "How do you test an endpoint that requires auth?" → MockMvc with a test JWT or @WithMockUser
- "What's the difference between @WebMvcTest and @SpringBootTest?" → slice vs full context, speed vs coverage

---

## Phase 6 — Advanced Queries — Aggregation & Dynamic filters
**Goal: Learn JPA query power and the Specification API**

### What the user can do
- See total spending per category for a given month
- See a monthly summary — income vs expenses per month for the last 12 months
- Filter transactions dynamically: combine any of date range + category + account + type
- Search transactions by description keyword

### Backend subjects covered
- Native SQL `@Query` with `GROUP BY`, `SUM`, `TRUNC(date, 'month')`
- JPA Projection interfaces — returning aggregated results without loading full entities
- `Specification<T>` API — building dynamic WHERE clauses in Java without string concatenation
- `JpaSpecificationExecutor<T>` — add to your repository to enable Specification queries
- `@NamedEntityGraph` — fine-grained control over what gets fetched per query type
- Index usage — understanding how your indexes on `(user_id, occurred_at DESC)` affect these queries

---

## Phase 7 — Budgets — Scheduled jobs & Cache invalidation
**Goal: Learn @Scheduled and proper @Cacheable usage**

### What the user can do
- Set a monthly budget for any category (e.g. €300 for "Food" in May 2025)
- See their actual spend vs budget for the current month
- See remaining budget per category
- Get notified (logged warning for now) when a budget is exceeded — checked nightly

### Backend subjects covered
- `@Scheduled` with cron expressions — `@Scheduled(cron = "0 0 2 * * *")` runs at 2am daily
- `@EnableScheduling` — how to activate scheduling in Spring
- `@Cacheable("budgetSummary")` — cache the spend-vs-budget result in Redis per user
- `@CacheEvict` — invalidate the cache when a new transaction is created (so the summary stays accurate)
- Cache key strategy — `@Cacheable(key = "#userId + '-' + #month")`
- Understanding TTL — when to expire vs when to evict manually

---

## Phase 8 — Excel Export — File generation & Async
**Goal: Learn file streaming and async processing**

### What the user can do
- Export their transactions for a date range as a `.xlsx` file (download)
- Export is processed asynchronously — for large exports, they get a job ID back and poll for completion

### Backend subjects covered
- **Apache POI** — creating `.xlsx` files in Java (rows, cells, formatting, header style)
- **`StreamingResponseBody`** — stream file bytes directly to the HTTP response without loading into memory
- **`Content-Disposition`** header — tells the browser to treat the response as a file download
- **`@Async`** + `CompletableFuture<T>` — run the export in a separate thread
- **`@EnableAsync`** — activating async support in Spring
- Thread pool configuration for async tasks — why you don't use the default thread pool in production

---

## Phase 9 — Role-Based Access Control (RBAC)
**Goal: Actually use the roles table you already have**

### What the user can do
- Regular users can only access their own data
- Admin users can list all users and view any account
- Attempting to access another user's data returns 403, not 404

### Backend subjects covered
- `@PreAuthorize("hasRole('ADMIN')")` — method-level security
- `@EnableMethodSecurity` — activating it in Spring Security
- `SecurityContextHolder` — extracting the current user's ID and roles inside a service
- Ownership validation pattern — checking `account.getUserId() == currentUserId` before any operation
- Returning 403 vs 404 — security best practice: don't reveal whether a resource exists

---

## Phase 10 — OAuth2 + Spring Profiles
**Goal: Social login and proper environment configuration**

### What the user can do
- Log in with Google or GitHub instead of email/password
- First OAuth2 login auto-creates their account

### Backend subjects covered
- `spring-security-oauth2-client` dependency
- OAuth2 Authorization Code Flow — what actually happens between browser, your server, and Google
- `OAuth2UserService` — customizing what happens after a successful OAuth2 login
- `application-dev.yml` vs `application-prod.yml` — profile-specific config
- `@Profile("dev")` — beans that only load in certain environments
- `@ConfigurationProperties` — binding a block of yml config to a typed Java class
- Environment variable best practices — never hardcode secrets, always externalize

---

## Phase 11 — API Documentation + Code Quality
**Goal: Make the project portfolio-ready and production-realistic**

### What you add (no new features)
- Swagger UI auto-generated from annotations
- Replace all raw `Exception` catches with typed exceptions
- Add `@Slf4j` everywhere (or manual Logger — pick one and be consistent)
- Add OpenAPI descriptions to every endpoint

### Backend subjects covered
- `springdoc-openapi` — one dependency, Swagger UI at `/swagger-ui.html`
- `@Operation`, `@ApiResponse`, `@Parameter` — documenting endpoints
- `@Schema` — documenting DTO fields
- Code review mindset — reading your own code as if you're reviewing a colleague's PR

---

## Java Language Gaps to fill in parallel
> These aren't phases — study them alongside the above. They come up in interviews independently of Spring.

| Topic | What to practice |
|---|---|
| Stream API | `map`, `filter`, `collect`, `groupingBy`, `flatMap` on collections |
| `Optional<T>` | Never call `.get()` without `.isPresent()`. Use `.orElseThrow()`, `.map()`, `.ifPresent()` |
| Generics | Write a generic `PageResponse<T>` wrapper yourself |
| Functional interfaces | `Function<T,R>`, `Predicate<T>`, `Consumer<T>`, `Supplier<T>` |
| `var` keyword | Java 10+ local variable type inference — know when it helps and when it hurts readability |
| Records | You already use them — understand equals/hashCode/toString are auto-generated |
| Sealed classes | Java 17 — good to know exists even if you don't use it |

---

## Subjects covered by the end (interview checklist)

| Subject | Phase |
|---|---|
| Dependency injection + bean lifecycle | Throughout |
| REST API design + HTTP semantics | 2 |
| JPA entity mapping + repositories | 2, 3, 4 |
| Flyway migrations | 1 |
| Custom validation + error handling | 2 |
| Self-referencing relationships | 3 |
| Recursive tree building in Java | 3 |
| `@Transactional` + propagation | 4 |
| Optimistic locking (`@Version`) | 4 |
| Concurrency / race conditions | 4 |
| N+1 query problem + fix | 4 |
| Pagination (`Pageable`) | 4 |
| `@ManyToMany` + explicit join entity | 4 |
| `CascadeType` + `orphanRemoval` | 4 |
| Unit tests with Mockito | 5 |
| Integration tests with MockMvc | 5 |
| Testcontainers | 5 |
| Aggregate queries (SUM, GROUP BY) | 6 |
| Specification API (dynamic filters) | 6 |
| `@Scheduled` + cron | 7 |
| `@Cacheable` / `@CacheEvict` (Redis) | 7 |
| Apache POI + file streaming | 8 |
| `@Async` + CompletableFuture | 8 |
| RBAC + `@PreAuthorize` | 9 |
| OAuth2 authorization code flow | 10 |
| Spring profiles + `@ConfigurationProperties` | 10 |
| Swagger / OpenAPI documentation | 11 |
| SLF4J logging | 2 (fix immediately) |
| Stream API + Optional + Generics | Parallel study |

---

## What you already have that most junior candidates don't
- Docker + docker-compose fully working
- Redis already configured and connected
- Flyway migrations (not ddl-auto)
- Separate signing keys for access vs refresh JWT
- Proper schema design with partial unique indexes
- `RestClient` for third-party API consumption
- 7 years of React = you understand REST from the consumer side deeply

That's a meaningful head start. Finish the roadmap, write the tests, and you'll be genuinely competitive.
