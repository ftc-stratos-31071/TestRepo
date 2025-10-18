# Turn to AprilTag (Limelight) - User Guide

## ✅ Setup Complete!

Your autonomous program is ready to use! The code compiles successfully and is ready to be deployed to your robot.

---

## 📋 Quick Start Checklist

### 1. **Robot Configuration (On Driver Station)**

Make sure your Robot Controller configuration has these hardware devices with **exact names**:

- ✅ **Motors:**
  - `front_left` - Front left drive motor
  - `front_right` - Front right drive motor
  - `back_left` - Back left drive motor
  - `back_right` - Back right drive motor

- ✅ **Sensors:**
  - `imu` - Control Hub IMU

**How to configure:**
1. Connect Driver Station to Robot Controller
2. Go to Configure Robot → Edit
3. Add/rename motors and IMU to match the names above

---

### 2. **Limelight Setup**

#### Physical Connection:
- Connect Limelight to Robot Controller via Ethernet or USB-Ethernet adapter
- Power the Limelight (typically via PoE or 12V barrel connector)

#### Software Configuration:
1. Open Limelight web interface at: `http://limelight.local:5801`
   - Or use the IP address shown on your Limelight
2. Create/select a pipeline for AprilTag detection:
   - Go to "Pipelines" tab
   - Select "AprilTag" detector type
   - Set family to **36h11** (FTC standard)
   - Save the pipeline
3. Note the pipeline number (0-9)

#### Update Code with Limelight Settings:
Open `TurnToLimelightAuto.java` and modify:
```java
private static final int TARGET_APRILTAG_ID = 5;  // Change to your target tag
private static final int LIMELIGHT_PIPELINE = 0;   // Change to your pipeline number
```

**Optional:** If Limelight doesn't connect at `limelight.local`, update the IP in `Limelight.java`:
```java
private static final String LIMELIGHT_IP = "10.12.34.11"; // Your Limelight IP
```

---

### 3. **Running the Program**

1. **Build and Deploy:**
   - In Android Studio, click "Run" (green play button)
   - Or connect to robot and select "Build → Make Project"

2. **On Driver Station:**
   - Select "Turn to AprilTag (Limelight)" from the Autonomous list
   - Place an AprilTag in front of the robot (matching your TARGET_APRILTAG_ID)
   - **Press INIT**
   - Check telemetry on Driver Station for:
     - ✅ "Status: Initialized"
     - ✅ "Target AprilTag ID: 5" (or your chosen ID)
   - **Press START**

3. **What Happens:**
   - Robot checks if AprilTag is visible
   - If found: Robot rotates smoothly to face the tag
   - If successful: Displays "SUCCESS - Target acquired!"
   - If timeout: Displays "TIMEOUT - Could not align"

---

## ⚙️ Configuration Options

### **AprilTag Selection**

In `TurnToLimelightAuto.java`, line ~67:

```java
private static final int TARGET_APRILTAG_ID = 5;
```

**Change this to:**
- `1`, `2`, `3` - Blue Alliance tags (DECODE 2025-2026)
- `4`, `5`, `6` - Red Alliance tags
- `-1` - Track ANY visible AprilTag (useful for testing)

---

### **PID Tuning (If Robot Doesn't Turn Smoothly)**

If the robot oscillates, overshoots, or turns too slowly, adjust these values in `TurnToLimelightAuto.java`:

```java
private static final double kP = 0.015;  // Proportional gain
private static final double kI = 0.0;    // Integral gain
private static final double kD = 0.001;  // Derivative gain
```

**Tuning Guide:**
- **Robot oscillates/shakes:** Decrease `kP` to `0.01` or lower
- **Robot turns too slowly:** Increase `kP` to `0.02` or higher
- **Robot overshoots target:** Increase `kD` to `0.003` or higher
- **Never quite reaches target:** Slightly increase `kI` to `0.0001`

**Power Settings:**
```java
private static final double MAX_POWER = 0.4;   // Maximum speed (40%)
private static final double MIN_POWER = 0.07;  // Minimum to overcome friction
```

- Increase `MAX_POWER` for faster rotation (up to 1.0 = 100%)
- Decrease for slower, smoother motion

---

### **Tolerance Settings**

How accurately the robot must align before stopping:

```java
private static final double LIMELIGHT_TOLERANCE = 1.5;  // ±1.5° on camera
private static final double HEADING_TOLERANCE = 2.0;    // ±2.0° on IMU
private static final double SETTLE_TIME_MS = 250.0;     // Must be stable for 250ms
```

- **More precise:** Decrease tolerances to `1.0` or `0.5`
- **Faster completion:** Increase to `3.0` or `5.0`

---

## 🔧 Troubleshooting

### **Problem: Robot rotates the WRONG direction**

If the robot turns right when it should turn left (or vice versa):

**Option 1: Swap motor directions in `MecanumDrive.java`** (line ~58):
```java
// BEFORE:
frontLeft.setDirection(DcMotor.Direction.REVERSE);
frontRight.setDirection(DcMotor.Direction.FORWARD);
backLeft.setDirection(DcMotor.Direction.REVERSE);
backRight.setDirection(DcMotor.Direction.FORWARD);

// AFTER (swap left/right):
frontLeft.setDirection(DcMotor.Direction.FORWARD);
frontRight.setDirection(DcMotor.Direction.REVERSE);
backLeft.setDirection(DcMotor.Direction.FORWARD);
backRight.setDirection(DcMotor.Direction.REVERSE);
```

**Option 2: Swap signs in `setPowerRotation()`** (line ~110):
```java
// BEFORE:
frontLeft.setPower(-power);
backLeft.setPower(-power);
frontRight.setPower(power);
backRight.setPower(power);

// AFTER:
frontLeft.setPower(power);    // Swapped sign
backLeft.setPower(power);      // Swapped sign
frontRight.setPower(-power);   // Swapped sign
backRight.setPower(-power);    // Swapped sign
```

---

### **Problem: "Target not detected!" error**

**Causes:**
1. ❌ AprilTag is not in Limelight's field of view
2. ❌ Wrong AprilTag ID (e.g., looking for ID 5 but showing ID 3)
3. ❌ Limelight not connected or pipeline not configured
4. ❌ Poor lighting conditions

**Solutions:**
- Check Driver Station telemetry for "Detected Tag ID" - if it shows a different number, either:
  - Change `TARGET_APRILTAG_ID` to match, OR
  - Use the correct AprilTag
- Temporarily set `TARGET_APRILTAG_ID = -1` to accept any tag
- Verify Limelight web interface shows green crosshair on the tag
- Add more lighting if in a dark room

---

### **Problem: IMU/Motor configuration errors**

**Error:** `Cannot find "imu"` or `Cannot find "front_left"`

**Solution:** 
Your Robot Controller configuration names don't match. Update your configuration on the Driver Station:
1. Go to Configure Robot
2. Rename devices to match: `imu`, `front_left`, `front_right`, `back_left`, `back_right`

**OR** modify the code to match your existing names in `TurnToLimelightAuto.java` (line ~111):
```java
IMU imu = hardwareMap.get(IMU.class, "YOUR_IMU_NAME");
```

And in `MecanumDrive.java` (lines ~51-54):
```java
frontLeft = hardwareMap.get(DcMotorEx.class, "YOUR_FRONT_LEFT_NAME");
// etc...
```

---

### **Problem: Robot doesn't rotate smoothly**

**Symptoms:**
- Jerky/stuttering motion
- Starts and stops repeatedly
- Oscillates around target

**Solutions:**
1. **Reduce PID gains:** Lower `kP` from `0.015` to `0.01` or `0.008`
2. **Increase settle time:** Change `SETTLE_TIME_MS` from `250` to `500`
3. **Check motor wiring:** Loose connections cause jerky motion
4. **Adjust MIN_POWER:** If robot can't overcome static friction, increase from `0.07` to `0.10`

---

### **Problem: Code won't compile/build**

Already fixed! Your project now builds successfully. If you get new errors:

1. **Clean and rebuild:**
   ```bash
   ./gradlew clean
   ./gradlew :TeamCode:assembleDebug
   ```

2. **Sync Gradle:** In Android Studio, click "Sync Project with Gradle Files"

3. **Check Java version:** Should be using Java 17 (configured in `gradle.properties`)

---

## 📊 Understanding Telemetry Output

During operation, you'll see these values on Driver Station:

```
Status: Aligning to target...
Current Heading: 45.23°       ← Robot's current direction
Target Heading: 50.15°        ← Where robot wants to point
Heading Error: 4.92°          ← How far off target
Limelight Yaw: 0.35°          ← How far tag is from camera center
PID Output: 0.087             ← Motor power being applied
Elapsed Time: 1.23 s          ← Time since start
In Tolerance: NO              ← Within acceptable error?
```

**What to watch:**
- **Heading Error** should decrease toward 0°
- **Limelight Yaw** should decrease toward 0°
- **PID Output** shows motor power (positive = CCW, negative = CW)
- When **In Tolerance: YES**, watch **Settle Progress** count up to 250ms

---

## 🎯 Testing Without Real Limelight

The code includes **mock data** for testing! The `Limelight.java` subsystem simulates detecting AprilTag ID 5 at the center.

**To test without hardware:**
1. The code will pretend it sees a tag at the center
2. Robot should rotate very little (already facing target)
3. Useful for testing motor directions and PID tuning

**To change simulated tag ID**, edit `Limelight.java` (line ~211):
```java
cachedTid = 5;  // Change to test different IDs
```

---

## 🚀 Next Steps

Once basic rotation works:

1. **Combine with path following:** Use AprilTag alignment at the end of an autonomous route
2. **Add distance control:** Use `getPitch()` from Limelight to control robot distance
3. **Score game elements:** After aligning, drive forward to place specimens/samples
4. **Multi-tag navigation:** Create sequences that align to different tags

---

## 📝 Files You Can Modify

| File | What to Change | When to Change |
|------|----------------|----------------|
| `TurnToLimelightAuto.java` | Target tag ID, PID gains, tolerances | Always - tune for your robot |
| `MecanumDrive.java` | Motor directions, IMU orientation | If robot drives/rotates wrong way |
| `Limelight.java` | IP address, mock data | If Limelight has custom IP |

---

## ✨ Key Features of Your Code

✅ **PID control** - Smooth, accurate rotation  
✅ **Target loss recovery** - Robot searches if tag disappears  
✅ **Specific tag tracking** - Only follows your chosen AprilTag ID  
✅ **Safety timeouts** - Won't get stuck in infinite loops  
✅ **Settle time** - Ensures robot is stable before stopping  
✅ **Comprehensive telemetry** - Easy to debug and tune  
✅ **Well-documented** - Comments explain everything  

---

## 📞 Getting Help

If you run into issues:

1. **Check telemetry output** on Driver Station
2. **Review this guide** for common problems
3. **Test in simulation** with mock data first
4. **Adjust one parameter at a time** when tuning

Good luck with your competition! 🏆

