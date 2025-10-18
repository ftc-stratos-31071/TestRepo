# Repository Context and Usage Guide

Purpose
- This folder contains the canonical documentation for your NextFTC-based codebase, colocated with the code.
- Use it as the single source of truth for architecture, subsystems, OpModes, commands, and control.

Where everything lives
- Canonical docs index: `README.md` in this folder
- Key code:
  - TeleOp: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/teleops/Teleop.java`
  - Subsystems: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/subsystems/{Lift,Claw}.java`
- Hardware names expected in RC configuration: `front_left`, `front_right`, `back_left`, `back_right`, `lift_motor`, `claw_servo`

How to use this context
1) Start at `README.md` (this folder) for the full table of contents.
2) For driver control, read `guide/opmodes/teleop.md` to understand joystick mapping and bindings.
3) For mechanisms, read `guide/subsystems/lift.md` and `guide/subsystems/claw.md` for commands and tuning.
4) To build and deploy, see `guide/installation.md`.
5) To extend behavior, review:
   - Commands: `nextftc/concepts/commands.md` and `nextftc/commands/*`
   - Components: `nextftc/concepts/components.md`
   - Control Systems: `control/usage/control-systems.md`

How to incorporate external links
- Add or update entries under `external/README.md` using the provided template.
- Summarize in your own words; include the URL and any parameters/config that affect this codebase.
- If content changes behavior or expectations, also update the relevant guide page (e.g., control tuning, subsystem docs).

Team workflow
- When you change code behavior (e.g., motor names, setpoints, bindings), update the relevant page here in the same commit.
- PRs should include doc updates when public behavior changes.
- Treat the files here as authoritative; external wikis should link back here.

Navigating the docs (quick links)
- Introduction: `guide/about.md`, `guide/installation.md`
- Subsystems: `guide/subsystems/overview.md`, `guide/subsystems/lift.md`, `guide/subsystems/claw.md`
- OpModes: `guide/opmodes/autonomous.md`, `guide/opmodes/teleop.md`
- Core Concepts: `nextftc/concepts/{commands,subsystems,components,opmodes,units,subsystem-groups}.md`
- Commands: `nextftc/commands/{groups,utilities,conditionals,delays,custom-commands}.md`
- Hardware: `nextftc/hardware/*`
- Control: `control/usage/*`, `control/examples.md`
- Extensions: `extensions/{pedro,roadrunner}.md`

Notes
- This context supersedes any duplicate docs outside TeamCode; consider the copies under this folder the canonical set.
- External resources (FTC SDK docs, vendor libraries, pathing frameworks) should be linked, not inlined, to avoid license issues. Paste brief summaries in your own words if needed.
