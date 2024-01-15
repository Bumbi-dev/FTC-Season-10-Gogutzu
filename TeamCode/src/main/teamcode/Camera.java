    package org.firstinspires.ftc.teamcode;

    import com.acmerobotics.dashboard.config.Config;
    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

    import org.firstinspires.ftc.teamcode.VecheaAutonomie.AutonomHardware;
    import org.firstinspires.ftc.vision.VisionPortal;

    @Config
    @Autonomous
    public class Camera extends AutonomHardware {

        public static int width = 200, leftX = 0, leftY = 150, middleX = 280, middleY = 0, rightX = 400, rightY = 250;
        private CSVisionProcessor visionProcessor;
        private VisionPortal visionPortal;

        @Override
        public void runOpMode() {

            init(hardwareMap);

            visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);//TODO

            visionPortal = VisionPortal.easyCreateWithDefaults((yoyo), visionProcessor);

            CSVisionProcessor.StartingPosition startingPos;

            while (!this.isStarted() && !this.isStopRequested()) {
                startingPos = visionProcessor.getStartingPosition();
                telemetry.addLine(CSVisionProcessor.avgLeft + " ");
                telemetry.addLine(CSVisionProcessor.avgMiddle + " ");
                telemetry.addLine(CSVisionProcessor.avgRight + " ");

                telemetry.addData("Identified", startingPos);
                telemetry.update();
            }

            visionPortal.stopStreaming();

            while (opModeIsActive()) {

            }
        }
    }