package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BACKBOARD-BLUE! (play)", group="play")
public class Autonomie_BackBoard_BLUE extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_blue_backboard";

        init(hardwareMap);
    }
}