package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import dev.nextftc.core.commands.Command;

/**
 * Command to follow a Pedro Path or PathChain.
 * This is the NextFTC-style implementation of FollowPath.
 */
public class FollowPath extends Command {
    private final Follower follower;
    private final Object path; // Can be Path or PathChain
    private boolean initialized = false;

    // Constructor with just PathChain
    public FollowPath(Follower follower, PathChain path) {
        this.follower = follower;
        this.path = path;
    }

    // Constructor with just Path
    public FollowPath(Follower follower, Path path) {
        this.follower = follower;
        this.path = path;
    }

    @Override
    public boolean isDone() {
        // Initialize the path following on first call
        if (!initialized) {
            if (path instanceof PathChain) {
                follower.followPath((PathChain) path);
            } else if (path instanceof Path) {
                follower.followPath((Path) path);
            }
            initialized = true;
        }

        // Update follower every loop - critical for Pedro to work!
        follower.update();

        // Command is done when follower is no longer busy
        return !follower.isBusy();
    }
}
