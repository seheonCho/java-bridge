package bridge.domain.bridgeTool;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeRandomNumberGeneratorTest {

    @DisplayName("0또는 1을 반환하는지 테스트")
    @Test
    void generateOnlyZeroAndOne() {
        BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
        int generate = bridgeNumberGenerator.generate();

        assertThat(generate).isIn(0, 1);
    }

}