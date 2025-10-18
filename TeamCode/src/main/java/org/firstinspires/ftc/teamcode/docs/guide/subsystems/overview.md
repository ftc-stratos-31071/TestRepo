# Subsystems Overview

Subsystems own hardware, expose Commands, and optionally implement `periodic()`.

Contract:
- Own hardware (e.g., `MotorEx`, `ServoEx`).
- Provide `Command`s.
- Use `requires(this)` on commands.
- Implement `periodic()` if closed-loop control is needed.

