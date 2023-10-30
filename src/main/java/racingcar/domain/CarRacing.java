package racingcar.domain;

import static racingcar.constant.RacingCarMessage.RACING_RESULT_MESSAGE;

import java.util.List;

public class CarRacing {
    private final Referee referee;
    private final RacingCarInputManager inputManager;
    private final RacingCarOutputManager outputManager;

    private CarRacing(Referee referee, RacingCarInputManager inputManager, RacingCarOutputManager outputManager) {
        this.referee = referee;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public static CarRacing init(Referee referee, RacingCarInputManager inputManager,
                                 RacingCarOutputManager outputManager) {
        return new CarRacing(referee, inputManager, outputManager);
    }


    public void start() {
        Cars cars = readCarInfo();
        Turn turn = readMovingTurn();
        move(turn, cars);

        List<String> winnerNames = judgeWinners(cars);
        announceWinners(winnerNames);
    }

    private void announceWinners(List<String> WinnerNames) {
        outputManager.printWinners(WinnerNames);
    }

    private List<String> judgeWinners(Cars cars) {
        return referee.judgeWinners(cars.getCurrentStatus());
    }

    private void move(Turn turn, Cars cars) {
        outputManager.println(RACING_RESULT_MESSAGE);

        List<Car> moveResult;
        for (int i = 0; i < turn.getCount(); i++) {
            moveResult = cars.tryToMove();
            for (Car car : moveResult) {
                outputManager.printCarPosition(car.getName(), car.getPosition());
            }
            outputManager.printEnter();
        }
    }

    private Turn readMovingTurn() {
        return new Turn(inputManager.readTryToMoveTurnCount());
    }

    private Cars readCarInfo() {
        return new Cars(inputManager.readCarNames());
    }
}
