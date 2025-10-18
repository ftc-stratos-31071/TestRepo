# Lift Subsystem

- Motor: `lift_motor`
- Control: position PID + optional elevator FF

Commands:
- `toLow` = 0
- `toMiddle` = 500
- `toHigh` = 1200

Tuning:
- Start with P only, add D to damp, add I for steady-state, consider FF for gravity.

Bindings:
- dpadUp → high, dpadLeft → mid, dpadDown → low

