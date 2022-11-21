package bridge.controller;

import bridge.domain.ClearStatus;
import bridge.domain.ErrorStatus;
import bridge.service.BridgeGame;
import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.List;

import static bridge.util.Constant.*;

public class BridgeGameController {

    private InputView inputView;
    private OutputView outputView = new OutputView();
    private BridgeGame bridgeGame;

    public BridgeGameController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.bridgeGame = new BridgeGame();
    }

    public void gameStart() {
        outputView.printGameStart();
        outputView.printDivisionLine();

        gameSetting(getMakeBridgeSize());
        outputView.printDivisionLine();

        gamePlay();
    }

    private int getMakeBridgeSize() {
        String input = "";
        do {
            input = inputView.readBridgeSize();
        } while (!validateInputSetting(input));

        return Integer.parseInt(input);
    }

    private void printErrorStatus(ErrorStatus errorStatus) {
        if (!errorStatus.isNotError()) {
//            outputView.printErrorMessage(errorStatus);
        }
    }

    private void gameSetting(int bridgeSize) {
        bridgeGame.initBridge(bridgeSize);
    }

    private boolean validateInputSetting(String userInput) {

        try {
            bridgeGame.validateBridgeSize(userInput);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }

        return true;
    }

    private void gamePlay() {
        do {
            moveAndResult();
            if (bridgeGame.isClearGame()) {
                break;
            }

        } while (bridgeGame.isRestartGame(getReadGameCommand()));

        getFinalResult();
    }

    private void moveAndResult() {
        bridgeGame.initPlayer();

        do {
            bridgeGame.initBridgeMap();
            bridgeGame.move(getMoveCommand());
            bridgeGame.makeBridgeMap();
            outputView.printMap(bridgeGame.getBridgeMap());//이동 결과 출력
            outputView.printDivisionLine();
        } while (bridgeGame.moveAgain());

    }

    private String getMoveCommand() {
        ErrorStatus errorStatus = ErrorStatus.INVALID_INPUT;
        String input = EMPTY;

        while (!errorStatus.isNotError()) {
            input = inputView.readMoveCommand();
            errorStatus = validateInputMoveCommand(input);
            printErrorStatus(errorStatus);
        }

        return input;
    }

    private ErrorStatus validateInputMoveCommand(String input) {
        return bridgeGame.validateInputMoveCommand(input);
    }

    private String getReadGameCommand() {
        ErrorStatus errorStatus = ErrorStatus.INVALID_INPUT;
        String input = EMPTY;

        while (!errorStatus.isNotError()) {
            input = inputView.readGameCommand();
            errorStatus = validateInputGameCommand(input);
            printErrorStatus(errorStatus);
        }

        return input;
    }

    private ErrorStatus validateInputGameCommand(String input) {
        return bridgeGame.validateInputGameCommand(input);
    }

    /**
     * 최종 결과 출력
     * */
    private void getFinalResult() {
        List<List<String>> bridgeMap = bridgeGame.getBridgeMap();

        boolean crossAllBridge = bridgeGame.isCrossAllBridge();
        ClearStatus clearStatus = ClearStatus.getMessageByClear(crossAllBridge);
        String clearMessage = clearStatus.getMessage();

        int tryCount = bridgeGame.getTryCount();

        outputView.printResult(bridgeMap, clearMessage, tryCount);
    }

}
