package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="BLUE! (play)", group="play")
public class Autonomie_Dezvoltata_BLUE extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_blue";

        init(hardwareMap);
    }
}