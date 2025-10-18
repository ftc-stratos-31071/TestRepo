package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

import dev.nextftc.core.subsystems.Subsystem;

/**
 * Limelight vision subsystem for AprilTag detection and tracking.
 *
 * The Limelight returns target data including:
 * - tx (yaw): horizontal offset from crosshair to target (positive = right, negative = left)
 * - ty (pitch): vertical offset
 * - tv: whether a valid target is detected (0 or 1)
 * - tid: AprilTag ID of detected target
 *
 * Configure your Limelight's IP address in the LIMELIGHT_IP constant.
 * Set the desired AprilTag ID using setTargetAprilTagId().
 *
 * SETUP INSTRUCTIONS:
 * 1. Connect Limelight to Robot Controller via Ethernet or Wi-Fi
 * 2. Set static IP on Limelight (typically 10.TE.AM.11 format)
 * 3. Update LIMELIGHT_IP below to match
 * 4. Configure Limelight pipeline for AprilTag detection
 * 5. Uncomment HTTP code in fetchLimelightData() for production
 *
 * APRILTAG PIPELINE SETUP:
 * 1. Open Limelight web interface (http://limelight.local:5801)
 * 2. Create or select a pipeline configured for AprilTag detection
 * 3. Set AprilTag family to 36h11 (standard for FTC)
 * 4. Note the pipeline number (0-9) and set it in your OpMode
 */
public class Limelight implements Subsystem {
    public static final Limelight INSTANCE = new Limelight();

    private Limelight() {}

    // TODO: Update this IP to match your Limelight's actual IP address
    // Find it in the Limelight web interface or via mDNS (limelight.local)
    private static final String LIMELIGHT_IP = "limelight.local"; // or "10.TE.AM.11" format

    // Cached values to avoid excessive HTTP calls
    private double cachedTx = 0.0;
    private double cachedTy = 0.0;
    private boolean cachedTv = false;
    private int cachedTid = -1;  // AprilTag ID (-1 = no tag detected)
    private final ElapsedTime cacheTimer = new ElapsedTime();
    private static final double CACHE_DURATION_MS = 50; // Refresh every 50ms max

    // Target AprilTag ID to track (set this in your OpMode)
    private int targetAprilTagId = -1;  // -1 = track any tag

    /**
     * Set the target AprilTag ID to track.
     * Set to -1 to track any visible AprilTag.
     *
     * LIMELIGHT JSON RESPONSE FORMAT:
     * {
     *   "Results": {
     *     "tx": 5.2,      // Horizontal offset in degrees
     *     "ty": -2.1,     // Vertical offset in degrees
     *     "tv": 1,        // Valid target (0 or 1)
     *     "tid": 3        // AprilTag ID (if AprilTag pipeline is active)
     *   }
     * }
     *
     * @param tagId the AprilTag ID to track (e.g., 1, 2, 3...), or -1 for any tag
     */
    public void setTargetAprilTagId(int tagId) {
        this.targetAprilTagId = tagId;
    }

    /**
     * Get the currently configured target AprilTag ID.
     *
     * @return the target tag ID, or -1 if tracking any tag
     */
    public int getTargetAprilTagId() {
        return targetAprilTagId;
    }

    /**
     * Get the horizontal offset (yaw) to the target in degrees.
     * Convention: positive = target is to the RIGHT, negative = target is to the LEFT
     *
     * @return horizontal offset in degrees, or 0.0 if no target detected
     */
    public double getYaw() {
        updateCache();
        return isTargetValid() ? cachedTx : 0.0;
    }

    /**
     * Get the vertical offset (pitch) to the target in degrees.
     *
     * @return vertical offset in degrees, or 0.0 if no target detected
     */
    public double getPitch() {
        updateCache();
        return isTargetValid() ? cachedTy : 0.0;
    }

    /**
     * Check if a valid target is currently detected.
     * If targetAprilTagId is set (not -1), only returns true if that specific tag is visible.
     *
     * @return true if target visible, false otherwise
     */
    public boolean hasTarget() {
        updateCache();
        return isTargetValid();
    }

    /**
     * Get the AprilTag ID of the currently detected tag.
     *
     * @return AprilTag ID, or -1 if no tag detected
     */
    public int getDetectedTagId() {
        updateCache();
        return cachedTid;
    }

    /**
     * Check if the detected tag matches the target (or if any tag is acceptable).
     *
     * @return true if detected tag is valid for tracking
     */
    private boolean isTargetValid() {
        if (!cachedTv) return false;  // No tag detected
        if (targetAprilTagId == -1) return true;  // Any tag is acceptable
        return cachedTid == targetAprilTagId;  // Check if it's the target tag
    }

    /**
     * Update cached values from Limelight if cache has expired.
     */
    private void updateCache() {
        if (cacheTimer.milliseconds() > CACHE_DURATION_MS) {
            fetchLimelightData();
            cacheTimer.reset();
        }
    }

    /**
     * Fetch fresh data from the Limelight.
     *
     * PRODUCTION IMPLEMENTATION:
     * Uncomment the HTTP code below once your Limelight is connected and configured.
     * You'll need to add these imports:
     * - import org.json.JSONObject;
     * - import java.io.BufferedReader;
     * - import java.io.InputStreamReader;
     * - import java.net.HttpURLConnection;
     * - import java.net.URL;
     *
     * Consider running this on a background thread to avoid blocking the main loop.
     */
    private void fetchLimelightData() {
        // STUB IMPLEMENTATION - Replace with actual HTTP call
        // String url = "http://" + LIMELIGHT_IP + ":5807/results";

        /*
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(100);
            conn.setReadTimeout(100);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject json = new JSONObject(response.toString());
                JSONObject results = json.getJSONObject("Results");

                cachedTx = results.optDouble("tx", 0.0);
                cachedTy = results.optDouble("ty", 0.0);
                cachedTv = results.optInt("tv", 0) == 1;
                cachedTid = results.optInt("tid", -1);  // Get AprilTag ID
            }
        } catch (Exception e) {
            // On error, keep previous cached values
            // Log error in production: RobotLog.ee("Limelight", e, "Failed to fetch data");
        }
        */

        // MOCK DATA for testing without Limelight hardware
        // Remove this section once you have real Limelight connected
        cachedTx = 0.0; // Simulate target at center
        cachedTy = 0.0;
        cachedTv = true; // Simulate target detected
        cachedTid = 5;  // Simulated AprilTag ID - change this to test different IDs
    }

    /**
     * Set the Limelight's LED mode via HTTP POST.
     * Uncomment for production use.
     *
     * @param mode 0=pipeline default, 1=force off, 2=force blink, 3=force on
     */
    public void setLEDMode(int mode) {
        // String url = "http://" + LIMELIGHT_IP + ":5807/led?ledMode=" + mode;
        // Send HTTP POST request to url
    }

    /**
     * Set the Limelight's pipeline (vision processing configuration).
     * Use this to switch between different AprilTag configurations or other vision modes.
     * Uncomment for production use.
     *
     * @param pipeline pipeline index (0-9)
     */
    public void setPipeline(int pipeline) {
        // String url = "http://" + LIMELIGHT_IP + ":5807/pipeline?pipeline=" + pipeline;
        // Send HTTP POST request to url
    }

    @Override
    public void periodic() {
        // No periodic updates needed; cache handles refresh timing
    }
}

