package bridge.service;

import bridge.domain.Bridge;
import bridge.domain.ErrorStatus;
import bridge.domain.Player;
import bridge.domain.bridgeTool.BridgeMaker;
import bridge.domain.bridgeTool.BridgeNumberGenerator;
import bridge.domain.bridgeTool.BridgeRandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

import static bridge.util.Constant.*;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

    private Bridge bridge;
    private Player player;
    private List<String> upperBridge = new ArrayList<>();
    private List<String> lowerBridge = new ArrayList<>();
    private int tryCount = 1;
    private boolean crossAllBridge;

    private BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
    private BridgeMaker bridgeMaker;

    public BridgeGame() {
        bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
    }

    public void initBridge(int bridgeSize) {
        this.bridge = new Bridge(bridgeMaker.makeBridge(bridgeSize));
    }

    public void initPlayer() {
        this.player = new Player();
    }

    public void initBridgeMap() {
        upperBridge = new ArrayList<>();
        lowerBridge = new ArrayList<>();
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void move(String moveCommand) {
        player.addChoice(moveCommand);
    }

    public void makeBridgeMap() {
        for (int position = 0; position < player.getNumberOfChoice(); position++) {
            String choice = getChoiceByPosition(position);
            makeUpperBridgeOfPart(position, choice);
            makeLowerBridgeOfPart(position, choice);
        }
    }

    private void makeUpperBridgeOfPart(int position, String choice) {
        if (!choice.equals(UP)) {
            upperBridge.add(SPACE);
            return;
        }

        if (choice.equals(getBridgeByPosition(position))) {
            upperBridge.add(ANSWER_RESULT);
            return;
        }
        upperBridge.add(WRONG_ANSWER_RESULT);
    }

    private void makeLowerBridgeOfPart(int position, String choice) {
        if (!choice.equals(DOWN)) {
            lowerBridge.add(SPACE);
            return;
        }

        if (choice.equals(getBridgeByPosition(position))) {
            lowerBridge.add(ANSWER_RESULT);
            return;
        }
        lowerBridge.add(WRONG_ANSWER_RESULT);
    }

    private String getChoiceByPosition(int position) {
        return player.getChoiceIndex(position);
    }
    
    private String getBridgeByPosition(int position) {
        return bridge.getBridgeByIndex(position);
    }

    public List<List<String>> getBridgeMap() {
        List<List<String>> bridgeMap = new ArrayList<>();
        bridgeMap.add(upperBridge);
        bridgeMap.add(lowerBridge);
        return bridgeMap;
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
        addTryCount();
    }

    private int getBridgeLength() {
        return bridge.getBridgeLength();
    }

    private int getNumberOfChoice() {
        return player.getNumberOfChoice();
    }

    public boolean moveAgain() {
        if (isAcrossLast()) {
            return false;
        }

        if (isSameRecentChoiceAndBridge()) {
            return true;
        }

        return false;
    }

    private boolean isSameRecentChoiceAndBridge() {
        return player.getLastChoice().equals(bridge.getBridgeByIndex(getNumberOfChoice() - 1));
    }

    private boolean isAcrossLast() {
        if (getBridgeLength() == getNumberOfChoice()) {
            return true;
        }
        return false;
    }

    public boolean isClearGame() {
        if (isAcrossLast() && isSameRecentChoiceAndBridge()) {
            crossAllBridge();
            return true;
        }
        return false;
    }

    private void crossAllBridge() {
        crossAllBridge = true;
    }

    public ErrorStatus validateBridgeSize(String input) {
        int number = Integer.MIN_VALUE;
        try {
            number = validateConvert(input);
        } catch (IllegalArgumentException e) {
            return ErrorStatus.INVALID_INPUT;
        }
        if (MIN_BRIDGE_SIZE > number || MAX_BRIDGE_SIZE < number) {
            return ErrorStatus.OUT_OF_RANGE;
        }
        return ErrorStatus.NOT_ERROR;
    }

    private int validateConvert(String input) {
        int number = Integer.MIN_VALUE;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        return number;
    }

    public ErrorStatus validateInputMoveCommand(String command) {
        if (!isMoveCommand(command)) {
            return ErrorStatus.INVALID_INPUT;
        }
        return ErrorStatus.NOT_ERROR;
    }

    private boolean isMoveCommand(String command) {
        if (!UP.equals(command) && !DOWN.equals(command)) {
            return false;
        }
        return true;
    }

    public ErrorStatus validateInputGameCommand(String input) {
        if (isEndCommand(input) || isRestartCommand(input)) {
            return ErrorStatus.NOT_ERROR;
        }
        return ErrorStatus.INVALID_INPUT;
    }

    public boolean isEndCommand(String command) {
        if (QUIT.equals(command)) {
            return true;
        }
        return false;
    }

    private boolean isRestartCommand(String command) {
        if (RESTART.equals(command)) {
            return true;
        }
        return false;
    }

    public boolean isRestartGame(String input) {
        if (RESTART.equals(input)) {
            retry();
            return true;
        }
        return false;
    }

    public void addTryCount() {
        this.tryCount = tryCount + 1;
    }

    public int getTryCount() {
        return tryCount;
    }

    public boolean isCrossAllBridge() {
        return crossAllBridge;
    }

}
