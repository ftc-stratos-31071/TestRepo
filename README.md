## Project Docs

For repository-specific NextFTC documentation (installation, subsystems, TeleOp, and more), see:

- TeamCode/src/main/java/org/firstinspires/ftc/teamcode/docs/README.md

## NOTICE

This repository contains the public FTC SDK for the DECODE (2025-2026) competition season.

## Welcome!
This GitHub repository contains the source code that is used to build an Android app to control a *FIRST* Tech Challenge competition robot.  To use this SDK, download/clone the entire project to your local computer.

## Requirements
To use this Android Studio project, you will need Android Studio Ladybug (2024.2) or later.

To program your robot in Blocks or OnBot Java, you do not need Android Studio.

## Getting Started
If you are new to robotics or new to the *FIRST* Tech Challenge, then you should consider reviewing the [FTC Blocks Tutorial](https://ftc-docs.firstinspires.org/programming_resources/blocks/Blocks-Tutorial.html) to get familiar with how to use the control system:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[FTC Blocks Online Tutorial](https://ftc-docs.firstinspires.org/programming_resources/blocks/Blocks-Tutorial.html)

Even if you are an advanced Java programmer, it is helpful to start with the [FTC Blocks tutorial](https://ftc-docs.firstinspires.org/programming_resources/blocks/Blocks-Tutorial.html), and then migrate to the [OnBot Java Tool](https://ftc-docs.firstinspires.org/programming_resources/onbot_java/OnBot-Java-Tutorial.html) or to [Android Studio](https://ftc-docs.firstinspires.org/programming_resources/android_studio_java/Android-Studio-Tutorial.html) afterwards.

## Downloading the Project
If you are an Android Studio programmer, there are several ways to download this repo.  Note that if you use the Blocks or OnBot Java Tool to program your robot, then you do not need to download this repository.

* If you are a git user, you can clone the most current version of the repository:

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;git clone https://github.com/FIRST-Tech-Challenge/FtcRobotController.git</p>

* Or, if you prefer, you can use the "Download Zip" button available through the main repository page.  Downloading the project as a .ZIP file will keep the size of the download manageable.

* You can also download the project folder (as a .zip or .tar.gz archive file) from the Downloads subsection of the [Releases](https://github.com/FIRST-Tech-Challenge/FtcRobotController/releases) page for this repository.

* The Releases page also contains prebuilt APKs.

Once you have downloaded and uncompressed (if needed) your folder, you can use Android Studio to import the folder  ("Import project (Eclipse ADT, Gradle, etc.)").

## Getting Help
### User Documentation and Tutorials
*FIRST* maintains online documentation with information and tutorials on how to use the *FIRST* Tech Challenge software and robot control system.  You can access this documentation using the following link:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[FIRST Tech Challenge Documentation](https://ftc-docs.firstinspires.org/index.html)

Note that the online documentation is an "evergreen" document that is constantly being updated and edited.  It contains the most current information about the *FIRST* Tech Challenge software and control system.

### Javadoc Reference Material
The Javadoc reference documentation for the FTC SDK is now available online.  Click on the following link to view the FTC SDK Javadoc documentation as a live website:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[FTC Javadoc Documentation](https://javadoc.io/doc/org.firstinspires.ftc)

### Online User Forum
For technical questions regarding the Control System or the FTC SDK, please visit the FIRST Tech Challenge Community site:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[FIRST Tech Challenge Community](https://ftc-community.firstinspires.org/)

### Sample OpModes
This project contains a large selection of Sample OpModes (robot code examples) which can be cut and pasted into your /teamcode folder to be used as-is, or modified to suit your team's needs.

Samples Folder: &nbsp;&nbsp; [/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples)

The readme.md file located in the [/TeamCode/src/main/java/org/firstinspires/ftc/teamcode](TeamCode/src/main/java/org/firstinspires/ftc/teamcode) folder contains an explanation of the sample naming convention, and instructions on how to copy them to your own project space.

# Release Information

## Version 11.0 (20250827-105138)

### Enhancements

* OnBotJava now has the concept of a project.  
  A project is a collection of related files.  A project may be chosen by selecting 'Example Project'
  from the 'File type:' dropdown.  Doing so will populate the dropdown to the immediate right with 
  a list of projects to choose from.
  When selecting a project all of the related files appear in the left pane of the workspace 
  underneath a directory with the chosen project name.
  This is useful for example for ConceptExternalHardwareClass which has a dependency upon
  RobotHardware.  This feature simplifies the usage of this Concept example by automatically
  pulling in dependent classes.
* Adds support for AndyMark ToF, IMU, and Color sensors.
* The Driver Station app indicates if WiFi is disabled on the device.
* Adds several features to the Color Processing software:
  * DECODE colors `ARTIFACT_GREEN` and `ARTIFACT_PURPLE`
  * Choice of the order of pre-processing steps Erode and Dilate
  * Best-fit preview shape called `circleFit`, an alternate to the existing `boxFit`
  * Sample OpMode `ConceptVisionColorLocator_Circle`, an alternate to the renamed `ConceptVisionColorLocator_Rectangle`
* The Driver Station app play button has a green background with a white play symbol if
  * the driver station and robot controller are connected and have the same team number
  * there is at least one gamepad attached
  * the timer is enabled (for an Autonomous OpMode)
* Updated AprilTag Library for DECODE. Notably, getCurrentGameTagLibrary() now returns DECODE tags.
  * Since the AprilTags on the Obelisk should not be used for localization, the ConceptAprilTagLocalization samples only use those tags without the name 'Obelisk' in them.
* OctoQuad I2C driver updated to support firmware v3.x 
  * Adds support for odometry localizer on MK2 hardware revision
  * Adds ability to track position for an absolute encoder across multiple rotations
  * Note that some driver APIs have changed; minor updates to user software may be required
  * Requires firmware v3.x. For instructions on updating firmware, see
    https://github.com/DigitalChickenLabs/OctoQuad/blob/master/documentation/OctoQuadDatasheet_Rev_3.0C.pdf


## Version 10.3 (20250625-090416)

### Breaking Changes
* The behavior of setGlobalErrorMsg() is changed.  Note that this is an SDK internal method that is not 
  meant to be used by team software or third party libraries.  Teams or libraries using this method should
  find another means to communicate failure.  The design intent of setGlobalErrorMsg() is to report an 
  error and force the user to restart the robot, which in certain circumstances when used inappropriately
  could cause a robot to continue running while Driver Station controls are disabled.  To prevent this,
  processing of a call to setGlobalErrorMsg() is deferred until the robot is in a known safe state.  This may
  mean that a call to setGlobalErrorMsg() that does not also result in stopping a running OpMode will appear
  as though nothing happened until the robot is stopped, at which point, if clearGlobalErrorMsg() has not 
  been called the message will appear on the Driver Station and a restart will be required.
  Addresses issue [1381](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1381)
* Fixes getLatestResult in Limelight3A so if the Limelight hasn't provided data yet, it still returns an LLResult but valid will be false
  * If you previously used to check and see if this was `null` to see if the Limelight had been contacted, you now need to use `isValid()` on the result.  That is because now it always returns an LLResult even before it talks to the Limelight, but if it doesn't have valid data, the `isValid()` will be `false`.
* Changed all omni samples to use front_left_drive, front_right_drive, back_left_drive, back_right_drive
  * This is only breaking for you if you copy one of the changed samples to your own project and expect to use the same robot configuration as before.

### Known Issues
* The redesigned OnBotJava new file workflow allows the user to use a lowercase letter as the first character of a filename.
  This is a regression from 10.2 which required the first character to be uppercase.  Software will build, but if the user tries
  to rename the file, the rename will fail.

### Enhancements
* Improved the OBJ new file creation flow workflow. The new flow allows you to easily use samples, craft new custom OpModes and make new Java classes.
* Added support for gamepad edge detection.
  * A new sample program `ConceptGamepadEdgeDetection` demonstrates its use.
* Adds a blackboard member to the Opmode that maintains state between opmodes (but not between robot resets).  See the ConceptBlackboard sample for how to use it.
* Updated PredominantColorProcessor to also return the predominant color in RGB, HSV and YCrCb color spaces.  Updated ConceptVisionColorSensor sample OpMode to display the getAnalysis() result in all three color spaces.
* Adds support for the GoBilda Pinpoint 
  * Also adds `SensorGoBildaPinpoint` sample to show how to use it
* Added `getArcLength()` and `getCircularity()` to ColorBlobLocatorProcessor.Blob.  Added BY_ARC_LENGTH and BY_CIRCULARITY as additional BlobCriteria.
* Added `filterByCriteria()` and `sortByCriteria()` to ColorBlobLocatorProcessor.Util.
  * The filter and sort methods for specific criteria have been deprecated.
  * The updated sample program `ConceptVisionColorLocator` provides more details on the new syntax.
* Add Help menu item and Help page that is available when connected to the robot controller via Program and Manage. The Help page has links to team resources such as [FTC Documentation](https://ftc-docs.firstinspires.org/), [FTC Discussion Forums](https://ftc-community.firstinspires.org), [Java FTC SDK API Documentation](https://javadoc.io/doc/org.firstinspires.ftc), and [FTC Game Information](https://ftc.game/).
* Self inspection changes:
  * List both the Driver Station Name and Robot Controller Name when inspecting the Driver Station.
  * Report if the team number portion of the device names do not match.
  * -rc is no longer valid as part of a Robot Controller name, must be -RC.
  * Use Robot Controller Name or Driver Station Name labels on the inspection screens instead of WIFI Access Point or WIFI Direct Name.

### Bug Fixes
* Fixes issue [1478](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1478) in AnnotatedHooksClassFilter that ignored exceptions if they occur in one of the SDK app hooks.
* Fix initialize in distance sensor (Rev 2m) to prevent bad data in first call to getDistance.
* Fixes issue [1470](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1470) Scaling a servo range is now irrespective of reverse() being called.  For example, if you set the scale range to [0.0, 0.5] and the servo is reversed, it will be from 0.5 to 0.0, NOT 1.0 to 0.5.
* Fixes issue [1232](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1232), a rare race condition where using the log rapidly along with other telemetry could cause a crash.

## Version 10.2 (20250121-174034)

### Enhancements

* Add ability to upload the pipeline for Limelight3A which allows teams to version control their limelight pipelines.


### Bug Fixes

* Fix an internal bug where if the RUN_TO_POSITION run mode was specified before a target position, recovery would require a power cycle. A side effect of this fix is that a stack trace identifying the location of the error is always produced in the log. Fixes issue [1345](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1345).
* Throws a helpful exception if region of interest is set to null when building a PredominantColorProcessor. Also sets the default RoI to the full frame. Addresses issue [1076](FIRST-Tech-Challenge/FtcRobotController#1076)
* Throws a helpful exception if user tries to construct an ImageRegion with malformed boundaries.  Addresses issue [1078](FIRST-Tech-Challenge/FtcRobotController#1078)

## Version 10.1.1 (20241102-092223)

### Breaking Changes

* Support for Android Studio Ladybug.  Requires Android Studio Ladybug.  

### Known Issues

* Android Studio Ladybug's bundled JDK is version 21.  JDK 21 has deprecated support for Java 1.8, and Ladybug will warn on this deprecation.
  OnBotJava only supports Java 1.8, therefore, in order to ensure that software developed using Android Studio will 
  run within the OnBotJava environment, the targetCompatibility and sourceCompatibility versions for the SDK have been left at VERSION_1_8.
  FIRST has decided that until it can devote the resources to migrating OnBotJava to a newer version of Java, the deprecation is the 
  lesser of two non-optimal situations.

### Enhancements

* Added `toString()` method to Pose2D
* Added `toString()` method to SparkFunOTOS.Pose2D

## Version 10.1 (20240919-122750)

### Enhancements
* Adds new OpenCV-based `VisionProcessor`s (which may be attached to a VisionPortal in either Java or Blocks) to help teams implement color processing via computer vision in the INTO THE DEEP game
  * `ColorBlobLocatorProcessor` implements OpenCV color "blob" detection. A new sample program `ConceptVisionColorLocator` demonstrates its use.
    * A choice is offered between pre-defined color ranges, or creating a custom one in RGB, HSV, or YCrCb color space
    * The ability is provided to restrict detection to a specified Region of Interest on the screen
    * Functions for applying erosion / dilation morphing to the threshold mask are provided
    * Functions for sorting and filtering the returned data are provided
  * `PredominantColorProcessor` allows using a region of the camera as a "long range color sensor" to determine the predominant color of that region. A new sample program `ConceptVisionColorSensor` demonstrates its use.
    * The determined predominant color is selected from a discrete set of color "swatches", similar to the MINDSTORMS NXT color sensor
  * Documentation on this Color Processing feature can be found here: https://ftc-docs.firstinspires.org/color-processing
* Added Blocks sample programs for color sensors: RobotAutoDriveToLine and SensorColor.
* Updated Self-Inspect to identify mismatched RC/DS software versions as a "caution" rather than a "failure."

### Bug Fixes
* Fixes [AngularVelocity conversion regression](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1070)

## Version 10.0  (20240828-111152)

### Breaking Changes
* Java classes and Blocks for TensorFlow Object Detection have been removed.
* `AngularVelocity.unit` which was of type `AngleUnit` has been renamed `AngularVelocity.angleUnit` of type `UnnormalizedAngleUnit`

### Enhancements
* Sample for REV Digital Indicator has been added - ConceptRevLED
* Adds support for the [Sparkfun QWIIC LED Stick](https://www.sparkfun.com/products/18354)
  * To connect it directly, you need this [cable](https://www.sparkfun.com/products/25596)
* Adds ConceptLEDStick OpMode
* Adds Blocks for colors black, blue, cyan, dkgray, gray, green, ltgray, magenta, red, white, and yellow.
* Adds an "evaluate but ignore result" Block that executes the connected block and ignores the result. Allows you to call a function and ignore the return value.
* Adds support for the GoBilda Pinpoint 
  * Also adds `SensorGoBildaPinpoint` sample to show how to use it
* Adds support for the REV 9-Axis IMU (REV-31-3332)
  * The REV 9-Axis IMU is only supported by the [Universal IMU interface](https://ftc-docs.firstinspires.org/en/latest/programming_resources/imu/imu.html)
  * Adds `Rev9AxisImuOrientationOnRobot` Java class.
  * If you mentally substitute this IMU's I2C port for the Control Hub's USB ports, `RevHubOrientationOnRobot` is also compatible with this sensor
  * Adds Blocks for Rev9AxisImuOrientationOnRobot, including RevHubImuOrientationOnRobot.xyzOrientation and  RevHubImuOrientationOnRobot.zyxOrientation.
  * Adds Blocks samples SensorRev9AxisIMUOrthogonal and SensorRev9AxisIMUNonOrthogonal.
* Improves Blocks support for RevHubImuOrientationOnRobot.
  * Adds Blocks for RevHubImuOrientationOnRobot.xyzOrientation and  RevHubImuOrientationOnRobot.zyxOrientation.
  * Adds Blocks samples SensorHubIMUOrthogonal (replaces SensorIMU) and SensorHubIMUNonOrthogonal.
* Updates EasyOpenCV, AprilTag, OpenCV, and `libjpeg-turbo` versions
* Adds Blocks for max and min that take two numbers.
* Adds Blocks OpModes ConceptRevSPARKMini, RobotAutoDriveByEncoder, RobotAutoDriveByGyro, RobotAutoDriveByTime, RobotAutoDriveToAprilTagOmni, and RobotAutoDriveToAprilTagTank.
* Two OpModes with the same name now automatically get renamed with the name followed by a "-" and the class name allowing them to both be on the device.
* Shows the name of the active configuration on the Manage page of the Robot Controller Console
* Updated AprilTag Library for INTO THE DEEP. Notably, `getCurrentGameTagLibrary()` now returns INTO THE DEEP tags.
* Adds Blocks for Telemetry.setMsTransmissionInterval and Telemetry.getMsTransmissionInterval.
* Adds Blocks sample SensorOctoQuad.

### Bug Fixes
* Fixes a bug where the RevBlinkinLedDriver Blocks were under Actuators in the Blocks editor toolbox. They are now Other Devices.
* Fixes a bug where `Exception`s thrown in user code after a stop was requested by the Driver Station would be silently eaten
* Fixed a bug where if you asked for `AngularVelocity` in a unit different than the device reported it in, it would normalize it between -PI and PI for radians, and -180 and 180 for degrees.

## Version 9.2 (20240701-085519)

### Important Notes
* Java classes and Blocks for TensorFlow Object Detection have been deprecated and will be removed in Version 10.0.
* The samples that use TensorFlow Object Detection have been removed.

### Enhancements
* Adds explanatory text to failed items on the inspection activities.  To view the explanatory text tap the red warning icon for a failed item.
* In the Blocks editor: added a new kind of variable set block that sets the variable and also returns the new value.
* Changes the way that camera controls behave for a SwitchableCamera. Now, each method (such as getExposure, getMinExposure, getMaxExposure, setExposure for ExposureControl) acts on the currently active camera.
* Adds support for the REV USB PS4 Compatible Gamepad (REV-31-2983)
* Adds ConceptAprilTagMultiPortal OpMode
* Adds support for OctoQuad Quadrature Encoder & Pulse Width Interface Module
* Adds the ExportAprilTagLibraryToBlocks annotation that indicates a static method that returns an AprilTagLibrary is exported to the Blocks programming environment. The corresponding block will appear in the Blocks toolbox along with the built-in tag libraries.
* Adds Blocks OpMode ConceptAprilTagOptimizeExposure.
* Adds support for the SparkFun Optical Tracking Odometry sensor.

### Bug Fixes
* Fixes https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/942 where visionPortal.close() can cause an IndexOutOfBoundsError.
* Fixes a bug in the blocks editor where collapsed function blocks show a warning "Collapsed blocks contain warnings." when the Blocks OpMode is reopened.
* Fixes a bug where the blocks editor wouldn't warn you that you have unsaved changes when you try to leave. This bug was introduced due to a behavior change in Chrome 119.
* Fixes a problem where OnBotJava does not apply font size settings to the editor.
* Updates EasyOpenCV dependency to v1.7.1
  * Fixes inability to use EasyOpenCV CameraFactory in OnBotJava
  * Fixes entire RC app crash when user pipeline throws an exception
  * Fixes entire RC app crash when user user canvas annotator throws an exception
  * Use the modern stacktrace display when handling user exceptions instead of the legacy ESTOP telemetry message

## Version 9.1 (20240215-115542)

### Enhancements
* Fixes a problem with Blocks: if the user closes a Block's warning balloon, it will still be closed next time the project is opened in the Blocks editor.
* In the Blocks editor, an alert concerning missing hardware devices is not shown if all the Blocks that use the missing hardware devices are disabled.
* Adds Blocks to support comparing property values CRServo.Direction, DCMotor.Direction, DCMotor.Mode, DCMotor.ZeroPowerBehavior, DigitalChannel.Mode, GyroSensor.HeadingMode, IrSeekerSensor.Mode, and Servo.Direction, to the corresponding enum Block.
* Improves OnBotJava auto-import to correctly import classes when used in certain situations.
* Improves OnBotJava autocomplete to provide better completion options in most cases.
  * This fixes an issue where autocomplete would fail if a method with two or more formal parameters was defined.
* In OnBotJava, code folding support was added to expand and collapse code sections
* In OnBotJava, the copyright header is now automatically collapsed loading new files
* For all Blocks OpMode samples, intro comments have been moved to the RunOpMode comment balloon.
* The Clean up Blocks command in the Blocks editor now positions function Blocks so their comment balloons don't overlap other function Blocks.
* Added Blocks OpMode sample SensorTouch.
* Added Java OpMode sample SensorDigitalTouch.
* Several improvements to VisionPortal
  * Adds option to control whether the stream is automatically started following a `.build()` call on a VisionPortal Builder
  * Adds option to control whether the vision processing statistics overlay is rendered or not
  * VisionPortals now implement the `CameraStreamSource` interface, allowing multiportal users to select which portal is routed to the DS in INIT by calling CameraStreamServer.getInstance().setSource(visionPortal). Can be selected via gamepad, between Camera Stream sessions.
  * Add option to `AprilTagProcessor` to suppress calibration warnings
  * Improves camera calibration warnings
    * If a calibration is scaled, the resolution it was scaled from will be listed
    * If calibrations exist with the wrong aspect ratio, the calibrated resolutions will be listed
  * Fixes race condition which caused app crash when calling `stopStreaming()` immediately followed by `close()` on a VisionPortal
  * Fixes IllegalStateException when calling `stopStreaming()` immediately after building a VisionPortal
  * Added FTC Blocks counterparts to new Java methods:
    * VisionPortal.Builder.setAutoStartStreamOnBuild
    * VisionPortal.Builder.setShowStatsOverlay
    * AprilTagProcessor.Builder.setSuppressCalibrationWarnings
    * CameraStreamServer.setSource​

### Bug Fixes
* Fixes a problem where OnBotJava does not apply font size settings to the editor.
* Updates EasyOpenCV dependency to v1.7.1
  * Fixes inability to use EasyOpenCV CameraFactory in OnBotJava
  * Fixes entire RC app crash when user pipeline throws an exception
  * Fixes entire RC app crash when user user canvas annotator throws an exception
  * Use the modern stacktrace display when handling user exceptions instead of the legacy ESTOP telemetry message

## Version 9.0.1 (20230929-083754)

### Enhancements
* Updates AprilTag samples to include Decimation and additional Comments.  Also corrects misleading tag ID warnings
* Increases maximum size of Blocks inline comments to 140 characters
* Adds Blocks sample BasicOmniOpMode.
* Updated CENTERSTAGE library AprilTag orientation quaternions
    * Thanks [@FromenActual](https://github.com/FromenActual)
* Updated Java Sample ConceptTensorFlowObjectDetection.java to include missing elements needed for custom model support.

### Bug Fixes
* Fixes a problem where after October 1 the Driver Station will report as obsolete on v9.0 and prompt the user to update.

## Version 9.0 (20230830-154348)

### Breaking Changes
* Removes Vuforia
* Fields in `AprilTagDetection` and `AprilTagPose(ftc/raw)` objects are now `final`
* VisionPortal builder method `setCameraMonitorViewId()` has been renamed to `setLiveViewContainerId()` and `enableCameraMonitoring()` has been renamed to `enableLiveView()`

### Enhancements
* Adds support for the DFRobot HuskyLens Vision Sensor.
* Blocks teams can now perform webcam calibration.
    * Added a Block for System.currentTimeMillis (under Utilities/Time)
    * Added a Block for VisionPortal.saveNextFrameRaw (under Vision/VisionPortal)
    * Added a new sample Blocks OpMode called UtilityCameraFrameCapture.
* The RobotDriveByGyro sample has been updated to use the new universal IMU interface.  It now supports both IMU types.
* Removed some error-prone ElapsedTime Blocks from the Blocks editor's toolbox. This is not a
  breaking change: old Blocks OpModes that use these Blocks will still function, both in the
  Blocks editor and at runtime.
* Standardizes on the form "OpMode" for the term OpMode.
    * The preferred way to refer to OpModes that specifically extend `LinearOpMode` (including Blocks OpModes) is "linear OpMode".
    * The preferred way to refer to OpModes that specifically extend `OpMode` directly is "iterative OpMode".
* Overhauls `OpMode` and `LinearOpMode` Javadoc comments to be easier to read and include more detail.
* Makes minor enhancements to Java samples
    * Javadoc comments in samples that could be rendered badly in Android Studio have been converted to standard multi-line comments
    * Consistency between samples has been improved
    * The SensorDigitalTouch sample has been replaced with a new SensorTouch sample that uses the `TouchSensor` interface instead of `DigitalChannel`.
    * The ConceptCompassCalibration, SensorMRCompass, and SensorMRIRSeeker samples have been deleted, as they are not useful for modern FTC competitions.

### Bug Fixes
* Fixes a bug which prevented PlayStation gamepads from being used in bluetooth mode. Bluetooth is NOT legal for competition but may be useful to allow a DS device to be used while charging, or at an outreach event.
* Fixes a bug where a Blocks OpMode's Date Modified value can change to December 31, 1969, if the Control Hub is rebooted while the Blocks OpMode is being edited.
* Fixes the automatic TeleOp preselection feature (was broken in 8.2)
* Fixes a bug where passing an integer number such as 123 to the Telemetry.addData block that takes a number shows up as 123.0 in the telemetry.
* Fixes OnBotJava autocomplete issues:
  * Autocomplete would incorrectly provide values for the current class when autocompleting a local variable
  * `hardwareMap` autocomplete would incorrectly include lambda class entries
* Fixes OnBotJava not automatically importing classes.
* Fixes OnBotJava tabs failing to close when their file is deleted.
* Fixes a project view refresh not happening when a file is renamed in OnBotJava.
* Fixes the "Download" context menu item for external libraries in the OnBotJava interface.
* Fixes issue where Driver Station telemetry would intermittently freeze when set to Monospace mode.
* Fixes performance regression for certain REV Hub operations that was introduced in version 8.2.
* Fixes TagID comparison logic in DriveToTag samples.

## Version 8.2 (20230707-131020)

### Breaking Changes
* Non-linear (iterative) OpModes are no longer allowed to manipulate actuators in their `stop()` method. Attempts to do so will be ignored and logged.
  * When an OpMode attempts to illegally manipulate an actuator, the Robot Controller will print a log message
    including the text `CANCELLED_FOR_SAFETY`.
  * Additionally, LinearOpModes are no longer able to regain the ability to manipulate actuators by removing their
    thread's interrupt or using another thread.
* Removes support for Android version 6.0 (Marshmallow). The minSdkVersion is now 24.
* Increases the Robocol version.
  * This means an 8.2 or later Robot Controller or Driver Station will not be able to communicate with an 8.1 or earlier Driver Station or Robot Controller.
  * If you forget to update both apps at the same time, an error message will be shown explaining which app is older and should be updated.
* FTC_FieldCoordinateSystemDefinition.pdf has been moved.  It is still in the git history, but has been removed from the git snapshot corresponding with the 8.2 tag.  The official version now lives at [Field Coordinate System](https://ftc-docs.firstinspires.org/field-coordinate-system).
* `LynxUsbDevice.addConfiguredModule()` and `LynxUsbDevice.getConfiguredModule()` have been replaced with `LynxUsbDevice.getOrAddModule()`.
* Old Blocks for Vuforia and TensorFlow Object Detection are obsolete and have been removed from the
  Blocks editor's toolbox. Existing Blocks OpModes that contain the old Blocks for Vuforia or
  TensorFlow Object Detection can be opened in the Blocks editor, but running them will not work.

### New features
* Adds new `VisionPortal` API for computer vision
    * **This API may be subject to change for final kickoff release!**
    * Several new samples added.
    * Adds support for detecting AprilTags.
    * `VisionPortal` is the new entry point for both AprilTag and TFOD processing.
    * Vuforia will be removed in a future release.
    * Updated TensorFlow dependencies.
    * Added support for webcam camera controls to blocks.
    * The Blocks editor's toolbox now has a Vision category, directly above the Utilities category.
* Related documentation for associated technologies can be found at
    * [AprilTag Introduction](https://ftc-docs.firstinspires.org/apriltag-intro)
    * [AprilTag SDK Guide](https://ftc-docs.firstinspires.org/apriltag-sdk)
    * [AprilTag Detection Values](https://ftc-docs.firstinspires.org/apriltag-detection-values)
    * [AprilTag Test Images](https://ftc-docs.firstinspires.org/apriltag-test-images)
    * [Camera Calibration](https://ftc-docs.firstinspires.org/camera-calibration)
* Adds Driver Station support for Logitech Dual Action and Sony PS5 DualSense gamepads.
    * This **does not** include support for the Sony PS5 DualSense Edge gamepad.
    * Always refer to Game Manual 1 to determine gamepad legality in competition.
* Adds support for MJPEG payload streaming to UVC driver (external JPEG decompression routine required for use).
* Shows a hint on the Driver Station UI about how to bind a gamepad when buttons are pressed or the sticks are moved on an unbound gamepad.
* Adds option for fullscreening "Camera Stream" on Driver Station.
* OnBotJava source code is automatically saved as a ZIP file on every build with a rolling window of the last 30 builds kept; allows recovering source code from previous builds if code is accidentally deleted or corrupted.
* Adds support for changing the addresses of Expansion Hubs that are not connected directly via USB.
  * The Expansion Hub Address Change screen now has an Apply button that changes the addresses without leaving the screen.
  * Addresses that are assigned to other hubs connected to the same USB connection or Control Hub are no longer able to be selected.
* Increases maximum size of Blocks inline comments to 100 characters
* Saves position of open Blocks comment balloons
* Adds new AprilTag Driving samples:  RobotDriveToAprilTagTank & RobotDriveToAprilTagOmni
* Adds Sample to illustrate optimizing camera exposure for AprilTags: ConceptAprilTagOptimizeExposure

### Bug Fixes
* Corrects inspection screen to report app version using the SDK version defined in the libraries instead of the version specified in `AndroidManifest.xml`. This corrects the case where the app could show matching versions numbers to the user but still state that the versions did not match.
  * If the version specified in `AndroidManifest.xml` does not match the SDK version, an SDK version entry will be displayed on the Manage webpage.
* Fixes no error being displayed when saving a configuration file with duplicate names from the Driver Station.
* Fixes a deadlock in the UVC driver which manifested in https://github.com/OpenFTC/EasyOpenCV/issues/57.
* Fixes a deadlock in the UVC driver that could occur when hot-plugging cameras.
* Fixes UVC driver compatibility with Arducam OV9281 global shutter camera.
* Fixes Emergency Stop condition when an OnBotJava build with duplicate OpMode names occurs.
* Fixes known causes of "Attempted use of a closed LynxModule instance" logspam.
* Fixes the visual identification LED pattern when configuring Expansion Hubs connected via RS-485.

## Version 8.1.1 (20221201-150726)

This is a bug fix only release to address the following four issues.

* [Issue #492](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/492) - Can't create new blocks opmodes.
* [Issue #495](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/495) - Remove the final modifier from the OpMode's Telemetry object.
* [Issue #500](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/500) - Some devices cannot be configured when the Driver Station app has been updated to 8.1
  * Updating either the Robot Controller app or the Driver Station app to 8.1.1 or later will fix this issue.
* The Modern Robotics touch sensor was configurable as a  Digital Device. It can only be used as an Analog Device.

## Version 8.1 (20221121-115119)

### Breaking Changes
* Deprecates the `OpMode` fields `msStuckDetectInit`, `msStuckDetectInitLoop`, `msStuckDetectStart`, `msStuckDetectLoop`, and `msStuckDetectStop`.
    * OpModes no longer have a time limit for `init()`, `init_loop()`, `start()` or `loop()`, so the fields corresponding to those methods are no longer used.
    * `stop()` still has a time limit, but it is now hardcoded to be 1 second, and cannot be changed using `msStuckDetectStop`.
* Deprecates the `OpMode` methods `internalPreInit()`, `internalPostInitLoop()`, and `internalPostLoop()`.
    * Iterative `OpMode`s will continue to call these methods in case they were overridden.
    * These methods will not be called at all for `LinearOpMode`s.
* Deprecates (and stops respecting) `DeviceProperties.xmlTagAliases`.

### Enhancements
* Adds a new `IMU` interface to Blocks and Java that can be used with both the original BNO055 IMU
  included in all older Control Hubs and Expansion Hubs, and the new alternative BHI260AP IMU.
  * You can determine which type of IMU is in your Control Hub by navigating to the Manage page of the web interface.
  * To learn how to use the new `IMU` interface, see https://ftc-docs.firstinspires.org/programming_resources/imu/imu.html. The `SensorIMU` Blocks sample was also updated to use the new `IMU` interface, and the following Java samples were added:
    * `SensorIMUOrthogonal`
      * Use this sample if your REV Hub is mounted so that it is parallel or perpendicular to the
        bottom of your robot.
    * `SensorIMUNonOrthogonal`
      * Use this sample if your REV Hub is mounted to your robot in any other orientation
    * `ConceptExploringIMUOrientations`
      * This OpMode is a tool to help you understand how the orthogonal orientations work, and
        which one applies to your robot.
  * The BHI260AP IMU can only be accessed via the new `IMU` interface. The BNO055 IMU can be
    programmed using the new `IMU` interface, or you can continue to program it using the old `BNO055IMU`
    interface. If you want to be able to quickly switch to a new Control Hub that may contain the
    BHI260AP IMU, you should migrate your code to use the new `IMU` interface.
  * Unlike the old `BNO055IMU` interface, which only worked correctly when the REV Hub was mounted flat
    on your robot, the `IMU` interface allows you to specify the orientation of the REV Hub on your
    robot. It will account for this, and give you your orientation in a Robot Coordinate System,
    instead of a special coordinate system for the REV Hub. As a result, your pitch and yaw will be
    0 when your *robot* is level, instead of when the REV Hub is level, which will result in much
    more reliable orientation angle values for most mounting orientations.
  * Because of the new robot-centric coordinate system, the pitch and roll angles returned by the
    `IMU` interface will be different from the ones returned by the `BNO055IMU` interface. When you are
    migrating your code, pay careful attention to the documentation.
  * If you have calibrated your BNO055, you can provide that calibration data to the new `IMU`
    interface by passing a `BNO055IMUNew.Parameters` instance to `IMU.initialize()`.
  * The `IMU` interface is also suitable for implementation by third-party vendors for IMUs that
    support providing the orientation in the form of a quaternion.
* Iterative `OpMode`s (as opposed to `LinearOpMode`s) now run on a dedicated thread.
    * Cycle times should not be as impacted by everything else going on in the system.
    * Slow `OpMode`s can no longer increase the amount of time it takes to process network commands, and vice versa.
    * The `init()`, `init_loop()`, `start()` and `loop()` methods no longer need to return within a certain time frame.
* BNO055 IMU legacy driver: restores the ability to initialize in one OpMode, and then have another OpMode re-use that
  initialization. This allows you to maintain the 0-yaw position between OpModes, if desired.
* Allows customized versions of device drivers in the FTC SDK to use the same XML tag.
  * Before, if you wanted to customize a device driver, you had to copy it to a new class _and_ give
    it a new XML tag. Giving it a new XML tag meant that to switch which driver was being used, you
    had to modify your configuration file.
  * Now, to use your custom driver, all you have to do is specify your custom driver's class when
    calling `hardwareMap.get()`. To go back to the original driver, specify the original driver
    class. If you specify an interface that is implemented by both the original driver and the
    custom driver, there is no guarantee about which implementation will be returned.

### Bug Fixes
* Fixes accessing the "Manage TensorFlow Lite Models" and "Manage Sounds" links and performing
  Blocks and OnBotJava OpMode downloads from the REV Hardware Client.
* Fixes issue where an I2C device driver would be auto-initialized using the parameters assigned in
  a previous OpMode run.
* Improves Driver Station popup menu placement in the landscape layout.
* Fixes NullPointerException when attempting to get a non-configured BNO055 IMU in a Blocks OpMode on an RC phone.
* Fixes problem with Blocks if a variable is named `orientation`.

## Version 8.0 (20220907-131644)

### Breaking Changes
* Increases the Robocol version.
  * This means an 8.0 or later Robot Controller or Driver Station will not be able to communicate with a 7.2 or earlier Driver Station or Robot Controller.
  * If you forget to update both apps at the same time, an error message will be shown explaining which app is older and should be updated.
* Initializing I2C devices now happens when you retrieve them from the `HardwareMap` for the first time.
  * Previously, all I2C devices would be initialized before the OpMode even began executing,
    whether you were actually going to use them or not. This could result in reduced performance and
    unnecessary warnings.
  * With this change, it is very important for Java users to retrieve all needed devices from the
    `HardwareMap` **during the Init phase of the OpMode**. Namely, declare a variable for each hardware
    device the OpMode will use, and assign a value to each. Do not do this during the Run phase, or your
    OpMode may briefly hang while the devices you are retrieving get initialized.
  * OpModes that do not use all of the I2C devices specified in the configuration file should take
    less time to initialize. OpModes that do use all of the specified I2C devices should take the
    same amount of time as previously.
* Fixes [issue #251](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/251) by changing the order in which axis rotation rates are read from the angular velocity vector in the BNO055 IMU driver.
* Deprecates the `pitchMode` in `BNO055IMU.Parameters`.
  * Setting `pitchMode` to `PitchMode.WINDOWS` would break the coordinate conventions used by the driver.
* Moves `OpModeManagerImpl` to the `com.qualcomm.robotcore.eventloop.opmode` package.
  * This breaks third party libraries EasyOpenCV (version 1.5.1 and earlier) and FTC Dashboard (version 0.4.4 and earlier).
* Deletes the deprecated `OpMode` method `resetStartTime()` (use `resetRuntime()` instead).
* Deletes the protected `LinearOpMode.LinearOpModeHelper` class (which was not meant for use by OpModes).
* Removes I2C Device (Synchronous) config type (deprecated since 2018)

### Enhancements
* Uncaught exceptions in OpModes no longer require a Restart Robot
  * A blue screen popping up with a stacktrace is not an SDK error; this replaces the red text in the telemetry area.
  * Since the very first SDK release, OpMode crashes have put the robot into "EMERGENCY STOP" state, only showing the first line of the exception, and requiring the user to press "Restart Robot" to continue
  * Exceptions during an OpMode now open a popup window with the same color scheme as the log viewer, containing 15 lines of the exception stacktrace to allow easily tracing down the offending line without needing to connect to view logs over ADB or scroll through large amounts of logs in the log viewer.
  * The exception text in the popup window is both zoomable and scrollable just like a webpage.
  * Pressing the "OK" button in the popup window will return to the main screen of the Driver Station and allow an OpMode to be run again immediately, without the need to perform a "Restart Robot"
* Adds new Java sample to demonstrate using a hardware class to abstract robot actuators, and share them across multiple OpModes.
  * Sample OpMode is [ConceptExternalHardwareClass.java](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptExternalHardwareClass.java)
  * Abstracted hardware class is [RobotHardware.java](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/RobotHardware.java)
* Updates RobotAutoDriveByGyro_Linear Java sample to use REV Control/Expansion hub IMU.
* Updates Vuforia samples to reference PowerPlay assets and have correct names and field locations of image targets.
* Updates TensorFlow samples to reference PowerPlay assets.
* Adds opt-in support for Java 8 language features to the OnBotJava editor.
  * To opt in, open the OnBotJava Settings, and check `Enable beta Java 8 support`.
  * Note that Java 8 code will only compile when the Robot Controller runs Android 7.0 Nougat or later.
  * Please report issues [here](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues).
* In OnBotJava, clicking on build errors now correctly jumps to the correct location.
* Improves OnBotJava autocomplete behavior, to provide better completion options in most cases.
* Adds a QR code to the Robot Controller Inspection Report when viewed from the Driver Station for scanning by inspectors at competition.
* Improves I2C performance and reliability in some scenarios.

## Version 7.2 (20220723-130006)

### Breaking Changes
* Updates the build tooling.  For Android Studio users, this change requires Android Studio Chipmunk 2021.2.1.
* Removes support for devices that are not competition legal, including Modern Robotics Core Control Modules, the Matrix Controller, and HiTechnic/NXT controllers and sensors.  Support remains for Modern Robotics I2C sensors.

### Enhancements
* Increases the height of the 3-dots Landscape menu touch area on the Driver Station, making it much easier to select.
* Adds `terminateOpModeNow()` method to allow OpModes to cleanly self-exit immediately.
* Adds `opModeInInit()` method to `LinearOpMode` to facilitate init-loops. Similar to `opModeIsActive()` but for the init phase.
* Warns user if they have a Logitech F310 gamepad connected that is set to DirectInput mode.
* Allows SPARKmini motor controllers to react more quickly to speed changes.
* Hides the version number of incorrectly installed sister app (i.e. DS installed on RC device or vice-versa) on inspection screen.
* Adds support to TensorFlow Object Detection for using a different frame generator, instead of Vuforia.
  Using Vuforia to pass the camera frame to TFOD is still supported.
* Removes usage of Renderscript.
* Fixes logspam on app startup of repeated stacktraces relating to `"Failed resolution of: Landroid/net/wifi/p2p/WifiP2pManager$DeviceInfoListener"`
* Allows disabling bluetooth radio from inspection screen
* Improves warning messages when I2C devices are not responding
* Adds support for controlling the RGB LED present on PS4/Etpark gamepads from OpModes
* Removes legacy Pushbot references from OpMode samples.  Renames "Pushbot" samples to "Robot".  Motor directions reversed to be compatible with "direct Drive" drive train.


### Bug fixes
* Fixes [issue #316](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/316) (MatrixF.inverted() returned an incorrectly-sized matrix for 1x1 and 2x2 matrixes).
* Self inspect now allows for Driver Station and Robot Controller compatibility between point releases.
* Fixes bug where if the same `RumbleEffect` object instance was queued for multiple gamepads, it
  could happen that both rumble commands would be sent to just one gamepad.
* Fixes bug in Driver Station where on the Driver Hub, if Advanced Gamepad Features was disabled and
  an officially supported gamepad was connected, then opening the Advanced Gamepad Features or
  Gamepad Type Overrides screens would cause the gamepad to be rebound by the custom USB driver even
  though advanced gamepad features was disabled.
* Protects against (unlikely) null pointer exception in Vuforia Localizer.
* Harden OnBotJava and Blocks saves to protect against save issues when disconnecting from Program and Manage
* Fixes issue where the RC app would hang if a REV Hub I2C write failed because the previous I2C
  operation was still in progress. This hang most commonly occurred during REV 2M Distance Sensor initialization
* Removes ConceptWebcam.java sample program.  This sample is not compatible with OnBotJava.
* Fixes bug where using html tags in an @ExportToBlocks comment field prevented the blocks editor from loading.
* Fixes blocks editor so it doesn't ask you to save when you haven't modified anything.
* Fixes uploading a very large blocks project to offline blocks editor.
* Fixes bug that caused blocks for DcMotorEx to be omitted from the blocks editor toolbox.
* Fixes [Blocks Programs Stripped of Blocks (due to using TensorFlow Label block)](https://ftcforum.firstinspires.org/forum/ftc-technology/blocks-programming/87035-blocks-programs-stripped-of-blocks)

## Version 7.1 (20211223-120805)

* Fixes crash when calling `isPwmEnabled()` ([issue #223](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/233)).
* Fixes lint error ([issue #4](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/4)).
* Fixes Driver Station crash when attempting to use DualShock4 v1 gamepad with Advanced Gamepad Features enabled ([issue #173](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/173)).
* Fixes possible (but unlikely) Driver Station crash when connecting gamepads of any type.
* Fixes bug where Driver Station would use generic 20% deadzone for Xbox360 and Logitech F310 gamepads when Advanced Gamepad Features was disabled.
* Added SimpleOmniDrive sample OpMode.
* Adds UVC white balance control API.
* Fixes [issue #259](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/259) Most blocks samples for TensorFlow can't be used for a different model.
    * The blocks previously labeled TensorFlowObjectDetectionFreightFrenzy (from the subcategory named "Optimized for Freight Frenzy") and TensorFlowObjectDetectionCustomModel (from the subcategory named "Custom Model") have been replaced with blocks labeled TensorFlowObjectDetection. Blocks in existing opmodes will be automatically updated to the new blocks when opened in the blocks editor.
* Fixes [issue #260](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/260) Blocks can't call java method that has a VuforiaLocalizer parameter.
    * Blocks now has a block labeled VuforiaFreightFrenzy.getVuforiaLocalizer for this.
* Added a page to manage the TensorFlow Lite models in /sdcard/FIRST/tflitemodels. To get to the TFLite Models page:
    * You can click on the link at the bottom of the Manage page.
    * You can click on the link at the upper-right the Blocks project page.
* Fixes logspam when `isBusy()` is called on a motor not in RTP mode.
* Hides the "RC Password" item on the inspection screen for phone-based Robot Controllers. (It is only applicable for Control Hubs).
* Adds channel 165 to Wi-Fi Direct channel selection menu in the settings screen. (165 was previously available through the web UI, but not locally in the app).

## Version 7.0 (20210915-141025)

### Enhancements and New Features
* Adds support for external libraries to OnBotJava and Blocks.
    * Upload .jar and .aar files in OnBotJava.
      * Known limitation - RobotController device must be running Android 7.0 or greater.
      * Known limitation - .aar files with assets are not supported.
    * External libraries can provide support for hardware devices by using the annotation in the
      com.qualcomm.robotcore.hardware.configuration.annotations package.
    * External libraries can include .so files for native code.
    * External libraries can be used from OnBotJava OpModes.
    * External libraries that use the following annotations can be used from Blocks OpModes.
      * org.firstinspires.ftc.robotcore.external.ExportClassToBlocks
      * org.firstinspires.ftc.robotcore.external.ExportToBlocks
    * External libraries that use the following annotations can add new hardware devices:
      * com.qualcomm.robotcore.hardware.configuration.annotations.AnalogSensorType
      * com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties
      * com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType
      * com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType
      * com.qualcomm.robotcore.hardware.configuration.annotations.MotorType
      * com.qualcomm.robotcore.hardware.configuration.annotations.ServoType
    * External libraries that use the following annotations can add new functionality to the Robot Controller:
      * org.firstinspires.ftc.ftccommon.external.OnCreate
      * org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
      * org.firstinspires.ftc.ftccommon.external.OnCreateMenu
      * org.firstinspires.ftc.ftccommon.external.OnDestroy
      * org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar
* Adds support for REV Robotics Driver Hub.
* Adds fully custom userspace USB gamepad driver to Driver Station (see "Advanced Gamepad Features" menu in DS settings).
    * Allows gamepads to work on devices without native Linux kernel support (e.g. some Romanian Motorola devices).
    * Allows the DS to read the unique serial number of each gamepad, enabling auto-recovery of dropped gamepads even if two gamepads of the same model drop. *(NOTE: unfortunately this does not apply to Etpark gamepads, because they do not have a unique serial)*.
    * Reading the unique serial number also provides the ability to configure the DS to assign gamepads to a certain position by default (so no need to do start+a/b at all).
    * The LED ring on the Xbox360 gamepad and the RGB LED bar on the PS4 gamepad is used to indicate the driver position the gamepad is bound to.
    * The rumble motors on the Xbox360, PS4, and Etpark gamepads can be controlled from OpModes.
    * The 2-point touchpad on the PS4 gamepad can be read from OpModes.
    * The "back" and "guide" buttons on the gamepad can now be safely bound to robot controls (Previously, on many devices, Android would intercept these buttons as home button presses and close the app).
    * Advanced Gamepad features are enabled by default, but may be disabled through the settings menu in order to revert to gamepad support provided natively by Android.
* Improves accuracy of ping measurement.
    * Fixes issue where the ping time showed as being higher than reality when initially connecting to or restarting the robot.
    * To see the full improvement, you must update both the Robot Controller and Driver Station apps.
* Updates samples located at [/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples).
    * Added ConceptGamepadRumble and ConceptGamepadTouchpad samples to illustrate the use of these new gampad capabilities.
    * Condensed existing Vuforia samples into just 2 samples (ConceptVuforiaFieldNavigation & ConceptVuforiaFieldNavigationWebcam) showing how to determine the robot's location on the field using Vuforia. These both use the current season's Target images.
    * Added ConceptVuforiaDriveToTargetWebcam to illustrate an easy way to drive directly to any visible Vuforia target.
* Makes many improvements to the warning system and individual warnings.
    * Warnings are now much more spaced out, so that they are easier to read.
    * New warnings were added for conditions that should be resolved before competing.
    * The mismatched apps warning now uses the major and minor app versions, not the version code.
    * The warnings are automatically re-enabled when a Robot Controller app from a new FTC season is installed.
* Adds support for I2C transactions on the Expansion Hub / Control Hub without specifying a register address.
    * See section 3 of the [TI I2C spec](https://www.ti.com/lit/an/slva704/slva704.pdf).
    * Calling these new methods when using Modern Robotics hardware will result in an UnsupportedOperationException.
* Changes VuforiaLocalizer `close()` method to be public.
* Adds support for TensorFlow v2 object detection models.
* Reduces ambiguity of the Self Inspect language and graphics.
* OnBotJava now warns about potentially unintended file overwrites.
* Improves behavior of the Wi-Fi band and channel selector on the Manage webpage.

### Bug fixes
 * Fixes Robot Controller app crash on Android 9+ when a Driver Station connects.
 * Fixes issue where an OpMode was responsible for calling shutdown on the
   TensorFlow TFObjectDetector. Now this is done automatically.
 * Fixes Vuforia initialization blocks to allow user to chose AxesOrder. Updated
   relevant blocks sample opmodes.
 * Fixes [FtcRobotController issue #114](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/114)
   LED blocks and Java class do not work.
 * Fixes match logging for OpModes that contain special characters in their names.
 * Fixes Driver Station OpMode controls becoming unresponsive if the Driver Station was set to the landscape layout and an OnBotJava build was triggered while an OpMode was running.
 * Fixes the Driver Station app closing itself when it is switched away from, or the screen is turned off.
 * Fixes "black swirl of doom" (Infinite "configuring Wi-Fi Direct" message) on older devices.
 * Updates the wiki comment on the OnBotJava intro page.

## Release 16.07.08

 * For the ftc_app project, the gradle files have been modified to support Android Studio 2.1.x.

## Release 16.03.30

 * For the MIT App Inventor, the design blocks have new icons that better represent the function of each design component.
 * Some changes were made to the shutdown logic to ensure the robust shutdown of some of our USB services.
 * A change was made to LinearOpMode so as to allow a given instance to be executed more than once, which is required for the App Inventor.
 * Javadoc improved/updated.

## Release 16.03.09

 * Changes made to make the FTC SDK synchronous (significant change!)
    - waitOneFullHardwareCycle() and waitForNextHardwareCycle() are no longer needed and have been deprecated.
    - runOpMode() (for a LinearOpMode) is now decoupled from the system's hardware read/write thread.
    - loop() (for an OpMode) is now decoupled from the system's hardware read/write thread.
    - Methods are synchronous.
    - For example, if you call setMode(DcMotorController.RunMode.RESET_ENCODERS) for a motor, the encoder is guaranteed to be reset when the method call is complete.
    - For legacy module (NXT compatible), user no longer has to toggle between read and write modes when reading from or writing to a legacy device.
 * Changes made to enhance reliability/robustness during ESD event.
 * Changes made to make code thread safe.
 * Debug keystore added so that user-generated robot controller APKs will all use the same signed key (to avoid conflicts if a team has multiple developer laptops for example).
 * Firmware version information for Modern Robotics modules are now logged.
 * Changes made to improve USB comm reliability and robustness.
 * Added support for voltage indicator for legacy (NXT-compatible) motor controllers.
 * Changes made to provide auto stop capabilities for OpModes.
    - A LinearOpMode class will stop when the statements in runOpMode() are complete.  User does not have to push the stop button on the driver station.
    - If an OpMode is stopped by the driver station, but there is a run away/uninterruptible thread persisting, the app will log an error message then force itself to crash to stop the runaway thread.
 * Driver Station UI modified to display lowest measured voltage below current voltage (12V battery).
 * Driver Station UI modified to have color background for current voltage (green=good, yellow=caution, red=danger, extremely low voltage).
 * javadoc improved (edits and additional classes).
 * Added app build time to About activity for driver station and robot controller apps.
 * Display local IP addresses on Driver Station About activity.
 * Incorporated a display filter on pairing screen to filter out devices that don’t use the “<TEAM NUMBER>-“ format. This filter can be turned off to show all Wi-Fi Direct devices.
 * Updated text in License file.
 * Fixed formatting error in OpticalDistanceSensor.toString().
 * Fixed issue on with a blank (“”) device name that would disrupt Wi-Fi Direct Pairing.
 * Improved javadoc generation.
 * Modified code to make it easier to support language localization in the future.

## Release 16.01.04

 * Updated compileSdkVersion for apps
 * Prevent Wi-Fi from entering power saving mode
 * removed unused import from driver station
 * Corrrected "Dead zone" joystick code.
 * LED.getDeviceName and .getConnectionInfo() return null
 * apps check for ROBOCOL_VERSION mismatch
 * Fix for Telemetry also has off-by-one errors in its data string sizing / short size limitations error
 * User telemetry output is sorted.
 * added formatting variants to DbgLog and RobotLog APIs
 * code modified to allow for a long list of OpMode names.
 * changes to improve thread safety of RobocolDatagramSocket
 * Fixes a problem where the Driver Station would exit after displaying the Configuring Wi-Fi Direct screen
 * Fixes Blocks and OnBotJava prompts when accessed via the REV Hardware Client

## Release 15.11.04.001

 * Added Support for Modern Robotics Gyro.
  - The GyroSensor class now supports the MR Gyro Sensor.
  - Users can access heading data (about Z axis)
  - Users can also access raw gyro data (X, Y, & Z axes).
  - Example MRGyroTest.java OpMode included.
 * Improved error messages
  - More descriptive error messages for exceptions in user code.
 * Updated DcMotor API
 * Enable read mode on new address in setI2cAddress
 * Fix so that driver station app resets the gamepads when switching OpModes.
 * USB-related code changes to make USB comm more responsive and to display more explicit error messages.
  - Fix so that USB will recover properly if the USB bus returns garbage data.
  - Fix USB initializtion race condition.
  - Better error reporting during FTDI open.
  - More explicit messages during USB failures.
  - Fixed bug so that USB device is closed if event loop teardown method was not called.
 * Fixed timer UI issue
 * Fixed duplicate name UI bug (Legacy Module configuration).
 * Fixed race condition in EventLoopManager.
 * Fix to keep references stable when updating gamepad.
 * For legacy Matrix motor/servo controllers removed necessity of appending "Motor" and "Servo" to controller names.
 * Updated HT color sensor driver to use constants from ModernRoboticsUsbLegacyModule class.
 * Updated MR color sensor driver to use constants from ModernRoboticsUsbDeviceInterfaceModule class.
 * Correctly handle I2C Address change in all color sensors
 * Updated/cleaned up OpModes.
  - Updated comments in LinearI2cAddressChange.java example OpMode.
  - Replaced the calls to "setChannelMode" with "setMode" (to match the new of the DcMotor  method).
  - Removed K9AutoTime.java OpMode.
  - Added MRGyroTest.java OpMode (demonstrates how to use MR Gyro Sensor).
  - Added MRRGBExample.java OpMode (demonstrates how to use MR Color Sensor).
  - Added HTRGBExample.java OpMode (demonstrates how to use HT legacy color sensor).
  - Added MatrixControllerDemo.java (demonstrates how to use legacy Matrix controller).
 * Updated javadoc documentation.
 * Updated release .apk files for Robot Controller and Driver Station apps.

## Release 15.10.06.002

 * Added support for Legacy Matrix 9.6V motor/servo controller.
 * Cleaned up build.gradle file.
 * Minor UI and bug fixes for driver station and robot controller apps.
 * Throws error if Ultrasonic sensor (NXT) is not configured for legacy module port 4 or 5.


## Release 15.08.03.001

 * New user interfaces for FTC Driver Station and FTC Robot Controller apps.
 * An init() method is added to the OpMode class.
   - For this release, init() is triggered right before the start() method.
   - Eventually, the init() method will be triggered when the user presses an "INIT" button on driver station.
   - The init() and loop() methods are now required (i.e., need to be overridden in the user's OpMode).
   - The start() and stop() methods are optional.
 * A new LinearOpMode class is introduced.
   - Teams can use the LinearOpMode mode to create a linear (not event driven) program model.
   - Teams can use blocking statements like Thread.sleep() within a linear OpMode.
 * The API for the Legacy Module and Core Device Interface Module have been updated.
   - Support for encoders with the Legacy Module is now working.
 * The hardware loop has been updated for better performance.
