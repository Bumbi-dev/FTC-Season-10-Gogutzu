package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous
public class Camera extends RobotHardware {

    public static int width = 300, leftX = 0, leftY = 0, middleX = 300, middleY = 0 , rightX = 702, rightY = 0;
    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        init(hardwareMap);

        visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);//TODO

        visionPortal = VisionPortal.easyCreateWithDefaults((yoyo), visionProcessor);

        CSVisionProcessor.StartingPosition startingPos;

        while (!this.isStarted() && !this.isStopRequested()) {
            telemetry.addLine(getRuntime() + " ");
            startingPos = visionProcessor.getStartingPosition();
            telemetry.addData("Identified", startingPos);
            telemetry.update();
        }

        visionPortal.stopStreaming();


        while (opModeIsActive()) {

        }
    }
}