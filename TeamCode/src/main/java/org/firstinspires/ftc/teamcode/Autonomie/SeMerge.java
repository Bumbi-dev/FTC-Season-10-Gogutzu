package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Autonomie.AutonomHardware;

import java.io.File;

@Autonomous(name="...", group="Bubu")
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