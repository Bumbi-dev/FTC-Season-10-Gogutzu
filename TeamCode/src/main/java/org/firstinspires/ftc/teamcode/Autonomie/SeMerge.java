package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.io.File;

@Autonomous(name="Merge", group="Bursuc")
public class SeMerge extends AutonomHardware {

    File movement = new File("/storage/emulated/0/FORST/movement");

    @Override
    public void runOpMode() {
        init(hardwareMap);

    }

    private void goToPosition (int x, int y) {
        //get motors to position, with some formula
    }
}