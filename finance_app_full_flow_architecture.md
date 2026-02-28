# 💰 Personal Finance App – Full Flow & Architecture

---

# 🧭 Overview

This application allows users to:
- Manage accounts (cash, bank, etc.)
- Organize spending using categories (hierarchical)
- Track transactions (income & expenses)
- Define budgets
- Analyze spending through reports

---

# 🧱 Core Entities

```
User
 ├── Accounts
 ├── Categories (tree)
 ├── Transactions
 ├── Budgets
 └── Reports (derived)
```

---

# 🚀 User Flow (Step-by-Step)

## 🔐 1. Authentication
User can:
- Register
- Login
- Refresh token
- Stay authenticated via JWT

---

## 💳 2. Accounts
User creates accounts such as:
- Cash
- ING Bank
- Revolut

Each transaction belongs to one account.

---

## 🗂️ 3. Categories (Hierarchical)

Categories support nesting:

```
Food
 ├── Groceries
 └── Restaurants

Transport
 ├── Fuel
 └── Uber
```

This is implemented using a self-referencing table (`parent_id`).

---

## 💸 4. Transactions

User records financial activity:

```
-50 RON → Groceries → Cash
-20 RON → Uber → Revolut
+3000 RON → Salary → ING
```

Each transaction:
- belongs to ONE account
- belongs to ONE category
- has a timestamp (`occurred_at`)

---

## 📊 5. Budgets

User defines limits per category:

```
Groceries → 800 RON / month
Transport → 400 RON / month
```

Budgets are tied to categories (not accounts).

---

## 📈 6. Dashboard / Insights

User can see:
- Total spending per month
- Spending per category
- Remaining budget
- Top spending categories
- Trends over time

---

## 🔍 7. Filtering & Queries

User can:
- Filter by date range
- Filter by account
- Filter by category
- Search transactions

---

## 🔄 8. Import (Advanced Feature)

User uploads CSV files:
- Transactions are imported
- Duplicate prevention via `external_id`
- Future: auto-categorization

---

## ⚡ 9. Concurrency Handling (Advanced)

System ensures:
- No double-spending
- Safe concurrent updates
- Data consistency

Techniques include:
- Transaction isolation
- Optimistic locking
- Proper DB constraints

---

# 🧩 Database Relationships

## Key relationships:

- User → Accounts (1:N)
- User → Categories (1:N)
- Category → Category (self-reference)
- Account → Transactions (1:N)
- Category → Transactions (1:N)
- Category → Budgets (1:N)

---

# 🧱 High-Level Architecture

```
[ User ]
    │
    ▼
[ Auth System (JWT) ]
    │
    ▼
[ Accounts ] ─────┐
                  │
[ Categories ] ───┼──▶ [ Transactions ] ───▶ [ Reports / Analytics ]
                  │
[ Budgets ] ──────┘
```

---

# 🔥 Advanced Concepts Covered

## Backend
- JWT authentication
- CSRF protection
- Filters & security chain
- Exception handling

## Database
- Self-referencing tables
- Composite indexes
- Partial indexes
- Aggregations (SUM, GROUP BY)

## Real-world logic
- Budget tracking
- Time-based queries
- Deduplication logic
- Concurrent updates

---

# 🧠 Development Strategy

## Phase 1 (Core)
- Users
- Accounts
- Categories
- Transactions

## Phase 2 (Intermediate)
- Budgets
- Basic reports
- Filtering

## Phase 3 (Advanced)
- Import system
- Deduplication
- Analytics
- Concurrency handling

---

# ✅ Notes

- Keep transactions simple initially (1 category per transaction)
- Add complexity (splits, advanced analytics) later
- Focus on clean DB design + correct relationships first

---

# 🚀 Next Steps

- Implement CRUD for all entities
- Add reporting queries
- Add indexes where needed
- Optimize queries based on usage

---

This project is strong enough to take you from intermediate to advanced in Spring Boot + SQL.

