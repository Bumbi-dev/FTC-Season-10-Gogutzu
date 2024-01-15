package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.VecheaAutonomie.AutonomHardware;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;

@Autonomous(name="Eu am facut autonomia-BLUE! (play)", group="play")
public class Autonomie_Dezvoltata_BLUE extends AutonomHardware {
    int nrRoti = 0;
    double time;
    String direction, roti;
    String[] partiRoti;
    File fila;
    public static int width = 200, leftX = 0, leftY = 50, middleX = 280, middleY = 0, rightX = 400, rightY = 250;
    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        autonomieNoua = true;
        init(hardwareMap);

        visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);//TODO
        visionPortal = VisionPortal.easyCreateWithDefaults(yoyo, visionProcessor);
        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.CENTER;

        while (!this.isStarted() && !this.isStopRequested()) {
            startingPos = visionProcessor.getStartingPosition();
            telemetry.addLine(CSVisionProcessor.avgLeft + " ");
            telemetry.addLine(CSVisionProcessor.avgMiddle + " ");
            telemetry.addLine(CSVisionProcessor.avgRight + " ");
            telemetry.addData("Identified", startingPos);
            telemetry.update();
        }
        visionPortal.stopStreaming();

        if(startingPos == CSVisionProcessor.StartingPosition.LEFT)
            nrAutonomie = 1;
        if(startingPos == CSVisionProcessor.StartingPosition.CENTER)
            nrAutonomie = 2;
        if(startingPos == CSVisionProcessor.StartingPosition.RIGHT)
            nrAutonomie = 3;

        telemetry.addLine(nrAutonomie + "");
        telemetry.update();

        path += nrAutonomie + ".txt";
        fila = new File(path);
        roti = ReadWriteFile.readFile(fila);
        partiRoti = roti.trim().split("\\s+");

        waitForStart();

        runtime.reset();

        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }

    private void nextVariables() {
        //if(nrRoti > partiRoti.length)
        //    requestOpModeStop();

        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]);
    }
    private void proceed(){
        runtime.reset();
        chooseDirection();

        while(runtime.seconds() < time)
            try {
                wait(1);
            }catch (Exception ignored){}
    }

    private void chooseDirection() {
        switch (direction) {
            case "f":
                moveForward();
                break;
            case "b":
                moveBack();
                break;
            case "l":
                strafeLeft();
                break;
            case "r":
                strafeRight();
                break;
            case "tl":
                rotateLeft();
                break;
            case "tr":
                rotateRight();
                break;
            case "u"://up
                liftArm();
                break;
            case "d"://down
                lowerArm();
                break;
            case "g"://grab
                closeClaw();
                break;
            case "dr"://drop
                openClaw();
                break;
            default:
                frana();
                break;
        }
    }
}