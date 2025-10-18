# Delays

NextFTC provides a `Delay` command for adding timing between steps in command sequences.

Usage from Auto.java:

```java
import dev.nextftc.core.commands.delays.Delay;

// Create a half-second delay
new Delay(0.5)

// Use in a sequence
new SequentialGroup(
    Claw.INSTANCE.close,
    new Delay(0.5),
    Lift.INSTANCE.toHigh
);
```

The `Delay` constructor takes a time in seconds (as a double). The command finishes when the elapsed time exceeds the specified duration.

Delays are particularly useful in autonomous routines to:
- Wait for a mechanism to settle before the next action
- Coordinate timing between parallel actions
- Add buffer time for sensor readings or vision processing

Avoid using `Thread.sleep()` in OpModes; use `Delay` commands instead so the command scheduler can manage timing properly and allow for graceful interruption.
