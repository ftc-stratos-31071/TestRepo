# Autonomous

File: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/autos/Auto.java`

Build autonomous routines by composing commands with `SequentialGroup` and `ParallelGroup`.

## Actual Implementation

```java
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;

@Autonomous(name = "NextFTC Autonomous Program Java")
public class Auto extends NextFTCOpMode {
    public Auto() {
        addComponents(
            new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
            BulkReadComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
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
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}
```

## Key Patterns

- Register subsystems via `SubsystemComponent` in the constructor
- Create a method that returns your full autonomous `Command`
- Schedule it in `onStartButtonPressed()`
- Use `SequentialGroup` for steps that must happen in order
- Use `ParallelGroup` to run multiple actions simultaneously
- Use `Delay` for timing between steps

## Tips

- Build and test each command independently before composing
- Use telemetry to debug which step is running
- Add timeouts to prevent runaway commands
- Consider using state machines for complex decision trees

