

# Conditionals

Drive behavior based on conditions and triggers:

- Gamepad triggers: `.whenBecomesTrue(cmd)`, `.whenBecomesFalse(cmd)`
- Analog thresholds: `.greaterThan(x)`, `.lessThan(x)`
- Custom predicates: build your own trigger streams from sensors.

Example:

```java
Gamepads.gamepad2().rightTrigger().greaterThan(0.2)
  .whenBecomesTrue(Claw.INSTANCE.close.then(Lift.INSTANCE.toHigh));
```
# Delays

Insert timing between steps using delay commands (implementation depends on your command utilities). Pattern:

```java
Command waitHalfSecond = Delays.seconds(0.5);
Claw.INSTANCE.close
  .then(waitHalfSecond)
  .then(Lift.INSTANCE.toHigh)
  .schedule();
```

If no delay helper exists, consider adding one using the OpMode clock and a simple `isFinished()` after elapsed time.

