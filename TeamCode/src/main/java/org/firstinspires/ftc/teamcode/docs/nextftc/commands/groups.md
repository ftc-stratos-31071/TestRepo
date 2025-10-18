# Command Groups

NextFTC provides explicit group classes for composing commands:

## Sequential Groups

Run commands one after another:

```java
import dev.nextftc.core.commands.groups.SequentialGroup;

new SequentialGroup(
    Lift.INSTANCE.toHigh,
    Claw.INSTANCE.open,
    Lift.INSTANCE.toLow
);
```

You can also use the fluent `.then()` method:
```java
Claw.INSTANCE.close.then(Lift.INSTANCE.toHigh);
```

## Parallel Groups

Run commands simultaneously; completes when all finish:

```java
import dev.nextftc.core.commands.groups.ParallelGroup;

new ParallelGroup(
    Claw.INSTANCE.open,
    Lift.INSTANCE.toLow
);
```

You can also use the fluent `.and()` method:
```java
Claw.INSTANCE.open.and(Lift.INSTANCE.toLow);
```

## Complex Example (from Auto.java)

```java
Command autonomousRoutine = new SequentialGroup(
    Lift.INSTANCE.toHigh,
    new ParallelGroup(
        Lift.INSTANCE.toMiddle,
        Claw.INSTANCE.close
    ),
    new Delay(0.5),
    new ParallelGroup(
        Claw.INSTANCE.open,
        Lift.INSTANCE.toLow
    )
);
```

This executes:
1. Lift to high (wait for completion)
2. Lower to middle while closing claw (parallel, wait for both)
3. Wait 0.5 seconds
4. Open claw while lowering lift (parallel, wait for both)
