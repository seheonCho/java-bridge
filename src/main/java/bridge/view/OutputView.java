package bridge.view;

import bridge.domain.ErrorStatus;

import java.util.List;
import java.util.StringJoiner;

import static bridge.util.Constant.*;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    public void printGameStart() {
        System.out.println("다리 건너기 게임을 시작합니다.");
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(List<List<String>> bridgeMap) {
        StringJoiner stringJoiner = initStringJoiner();
        for (List<String> partOfBridge : bridgeMap) {

            for (String bridgeOfPosition : partOfBridge) {
                stringJoiner.add(bridgeOfPosition);
            }

            System.out.println(stringJoiner);
            stringJoiner = initStringJoiner();
        }
    }

    private StringJoiner initStringJoiner() {
        return new StringJoiner(DELIMITER, VIEW_PREFIX, VIEW_SUFFIX);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(List<List<String>> bridgeMap, String clearMessage, int tryCount) {
        System.out.println("최종 게임 결과");
        printMap(bridgeMap);

        printDivisionLine();

        System.out.println("게임 성공 여부: " + clearMessage);
        System.out.println("총 시도한 횟수: " + tryCount);
    }

    public void printDivisionLine() {
        System.out.println();
    }
}
