package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import dev.nextftc.core.commands.Command;

/**
 * NextFTC Command to follow a Pedro Pathing PathChain or Path.
 *
 * This command integrates Pedro Pathing 2.0.4 with the NextFTC command system.
 *
 * Example usage:
 * <pre>
 *   PathChain myPath = follower.pathBuilder()
 *       .addPath(new BezierLine(new Pose(0, 0), new Pose(24, 24)))
 *       .build();
 *
 *   new PedroPathingCommand(follower, myPath).schedule();
 * </pre>
 */
public class PedroPathingCommand extends Command {
    private final Follower follower;
    private final PathChain pathChain;
    private final Path path;
    private final boolean isPathChain;
    private boolean hasStarted = false;

    /**
     * Constructor for PathChain (recommended for most use cases)
     */
    public PedroPathingCommand(Follower follower, PathChain pathChain) {
        this.follower = follower;
        this.pathChain = pathChain;
        this.path = null;
        this.isPathChain = true;
    }

    /**
     * Constructor for single Path
     */
    public PedroPathingCommand(Follower follower, Path path) {
        this.follower = follower;
        this.pathChain = null;
        this.path = path;
        this.isPathChain = false;
    }

    @Override
    public boolean isDone() {
        // Initialize on first call
        if (!hasStarted) {
            if (isPathChain) {
                follower.followPath(pathChain);
            } else {
                follower.followPath(path);
            }
            hasStarted = true;
        }

        // Update follower every loop - critical for Pedro to work!
        follower.update();

        // Command is done when follower is no longer busy
        return !follower.isBusy();
    }
}
