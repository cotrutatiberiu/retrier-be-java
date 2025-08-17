param(
  [string]$Path = ".env"
)

# Check if the .env file exists
if (-not (Test-Path $Path)) {
  Write-Host "Error: .env file not found at $Path"
  return
}

# Read each line from the .env file
Get-Content -Path $Path | ForEach-Object {
  # Ignore comments and empty lines
  if ($_ -notmatch '^\s*#|^\s*$' -and $_ -match '=') {
    # Split the line into key and value
    $key, $value = $_ -split '=', 2
    # Trim whitespace and quotes from the key and value
    $key = $key.Trim()
    $value = $value.Trim('"', "'")

    # Set the environment variable for the current session
    [Environment]::SetEnvironmentVariable($key, $value, "Process")

    Write-Host "Set environment variable: $key=$value"
  }
}