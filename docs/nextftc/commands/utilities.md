# Utilities

Common utility patterns with commands:

- Telemetry heartbeat: a lightweight command that updates telemetry periodically.
- Safety stop: a command that stops motors when canceled or on error.
- Watchdogs: timeouts that cancel long-running commands.

These can be composed with `.and(...)` to run alongside primary actions.

