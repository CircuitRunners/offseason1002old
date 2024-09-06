package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Autonomous(name = "Simple Auto Blue", group = "Examples")
public class SimpleAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer;
    private int pathState;

    // Define key poses
    private Pose pointOne = new Pose(8.5, 84, 0);
    private Pose pointTwo = new Pose(52, 104, Math.toRadians(270));
    private Pose pointThree = new Pose(44, 121.75, Math.toRadians(270));
    private Pose pointFour = new Pose(58, 121.25, Math.toRadians(270));

    // Define the three main paths
    private Path pathOne, pathTwo, pathThree;

    public void buildPaths() {
        pathOne = new Path(new BezierCurve(new Point(pointOne), new Point(48, 135, Point.CARTESIAN), new Point(pointTwo)));
        pathOne.setLinearHeadingInterpolation(pointOne.getHeading(), pointTwo.getHeading());

        pathTwo = new Path(new BezierLine(new Point(pointTwo), new Point(pointThree)));
        pathTwo.setLinearHeadingInterpolation(pointTwo.getHeading(), pointThree.getHeading());

        pathThree = new Path(new BezierLine(new Point(pointThree), new Point(pointFour)));
        pathThree.setConstantHeadingInterpolation(pointThree.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(pathOne);
                setPathState(1);
                break;
            case 1:
                follower.followPath(pathTwo);
                setPathState(2);
                break;
            case 2:
                follower.followPath(pathThree);
                setPathState(3);
                break;
            case 3:
                // Auto complete
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        follower = new Follower(hardwareMap);
        follower.setStartingPose(pointOne);
        buildPaths();
    }

    @Override
    public void start() {
        pathTimer.resetTimer();
        setPathState(0);
    }
}