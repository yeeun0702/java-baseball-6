package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {
    public static final int NUMBER_LENGTH = 3;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 9;

    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");

        while (true) {
            String computerNum = generateRandomNumber();
            try {
                boolean continueGame = playGame(computerNum);
                if (!continueGame) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    public static String generateRandomNumber() {
        return IntStream.generate(() -> Randoms.pickNumberInRange(MIN_NUMBER, MAX_NUMBER))
                .distinct()
                .limit(NUMBER_LENGTH)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    public static boolean playGame(String computerNum) {
        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            String userNum = Console.readLine();

            if (userNum.length() != NUMBER_LENGTH) {
                throw new IllegalArgumentException("1에서 9까지로 이루어진 3자리 숫자를 입력해주세요.");
            }

            long strikeCount = IntStream.range(0, NUMBER_LENGTH)
                    .filter(i -> userNum.charAt(i) == computerNum.charAt(i))
                    .count();
            long ballCount = IntStream.range(0, NUMBER_LENGTH)
                    .filter(i -> userNum.charAt(i) != computerNum.charAt(i) && computerNum.contains(String.valueOf(userNum.charAt(i))))
                    .count();

            if (processResult((int) strikeCount, (int) ballCount)) {
                return askForGameRestart();
            }
        }
    }

    private static boolean processResult(int strikeCount, int ballCount) {
        if (strikeCount == NUMBER_LENGTH) {
            System.out.println(strikeCount + "스트라이크");
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            return true;
        }

        if (ballCount > 0) {
            System.out.print(ballCount + "볼 ");
        }

        if (strikeCount > 0) {
            System.out.println(strikeCount + "스트라이크");
        } else if (ballCount == 0) {
            System.out.println("낫싱");
        }

        return false;
    }

    private static boolean askForGameRestart() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        int userChoose = Integer.parseInt(Console.readLine());
        return userChoose == 1;
    }
}
