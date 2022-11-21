package bridge.domain;

import bridge.util.BridgeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @DisplayName("choice 를 추가한다.")
    @Test
    void addChoice() {
        Player player = new Player();
        player.addChoice(BridgeUtil.UP);

        assertThat(player.getNumberOfChoice()).isEqualTo(1);
    }

    @DisplayName("지정한 인덱스의 chocie 를 가져온다.")
    @Test
    void getChoiceByPosition() {
        Player player = new Player();
        player.addChoice(BridgeUtil.UP);
        player.addChoice(BridgeUtil.DOWN);
        player.addChoice(BridgeUtil.UP);

        assertThat(player.getChoiceIndex(1)).isEqualTo(BridgeUtil.DOWN);
    }

    @DisplayName("Player 클래스의, choice 개수를 가져온다.")
    @Test
    void getNumberOfChoice() {
        Player player = new Player();
        player.addChoice(BridgeUtil.UP);
        player.addChoice(BridgeUtil.DOWN);
        player.addChoice(BridgeUtil.UP);

        assertThat(player.getNumberOfChoice()).isEqualTo(3);
    }

    @DisplayName("가장 마지막 choice 를 가져온다.")
    @Test
    void getLastChoice() {
        Player player = new Player();
        player.addChoice(BridgeUtil.UP);
        player.addChoice(BridgeUtil.DOWN);
        player.addChoice(BridgeUtil.UP);

        assertThat(player.getLastChoice()).isEqualTo(BridgeUtil.UP);
    }

}