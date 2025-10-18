# Command Groups

Compose commands to build complex behaviors:

- Sequential: `a.then(b).then(c)`
- Parallel: `a.and(b)`
- Mix: `(a.then(b)).and(c)`

Example:

```java
Command deposit = Claw.INSTANCE.close
  .then(Lift.INSTANCE.toHigh)
  .then(Claw.INSTANCE.open)
  .then(Lift.INSTANCE.toLow);

deposit.schedule();
```

Termination:

- Sequential completes when the last step finishes.
- Parallel completes when all members finish (unless you choose a variant that completes on any).

