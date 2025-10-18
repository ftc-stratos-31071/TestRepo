# Pedro Pathing Extension

Pedro Pathing is a motion planning library for FTC. This repo includes an example showing how to integrate it with NextFTC.

File: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/autos/Pedro.java`

## Integration Pattern

```java
import dev.nextftc.extensions.pedro.PedroComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "Pedro Auto")
public class Pedro extends NextFTCOpMode {
    public Pedro() {
        addComponents(
            // Uncomment when Pedro is fully configured:
            // new PedroComponent(Constants::createFollower),
            new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
            BulkReadComponent.INSTANCE
        );
    }
    
    // Use follower() to access Pedro's Follower instance
    // Compose path-following with manipulator commands
}
```

## Steps to Enable

1. Add Pedro Pathing library as a dependency
2. Create a `Constants` class with `createFollower()` method
3. Uncomment `PedroComponent` in your autonomous OpMode
4. Use `follower()` static method to access the Follower
5. Wrap Pedro path commands and compose with subsystem commands

See subpages for detailed workflows:
- [Getting Started](pedro/getting-started.md)
- [Following Paths](pedro/following-paths.md)
- [Turning](pedro/turning.md)
- [TeleOp Driving](pedro/teleop.md)
