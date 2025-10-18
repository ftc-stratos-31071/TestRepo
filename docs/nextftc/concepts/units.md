# Units

NextFTC APIs may use physical units (ticks, rotations, seconds) within `KineticState` or control elements. Keep a consistent convention:

- Encoders: define ticks-per-rotation and gear ratio once.
- Distances: prefer inches or meters consistently; convert at subsystem boundaries.
- Time: seconds as doubles.

Tip: Create small helpers to convert between user-friendly units and device-native units.

