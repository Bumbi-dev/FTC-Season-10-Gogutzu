package org.firstinspires.ftc.LTASrob;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.LTASrob.VecheaAutonomie.AutonomHardware;

import java.io.File;

@Autonomous(name="Eu am facut autonomia-BLUE! (play)", group="play")
public class Autonomie_Dezvoltata_BLUE extends AutonomHardware {
    int nrRoti = 0;
    double time;
    String direction, roti;
    String[] partiRoti;

    @Override
    public void runOpMode() {
        init(hardwareMap);

        path += nrAutonomie + "_blue.txt";
        roti = ReadWriteFile.readFile(new File(path));
        partiRoti = roti.trim().split("\\s+");

        waitForStart();

        runtime.reset();

        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }

    private void nextVariables() {
        //TODO
        //if(nrRoti > partiRoti.length)
        //    requestOpModeStop();

        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]);
    }
    private void proceed(){
        runtime.reset();
        while(runtime.seconds() < time)
            chooseDirection();
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
            case "grabLeft"://grab
                setLeftServoPosition(ServoPositions.CLOSE);
                break;
            case "grabRight"://drop
                setRightServoPosition(ServoPositions.CLOSE);
                break;
            case "dropLeft"://grab
                setLeftServoPosition(ServoPositions.OPEN);
                break;
            case "dropRight"://drop
                setRightServoPosition(ServoPositions.OPEN);
                break;
            default:
                frana();
                break;
        }
    }
}