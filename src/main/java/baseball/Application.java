package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static final int NUMBER_LENGTH = 3;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 9;

    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        while (true) {
            String computerNum = generateRandomNumber();
            playGame(computerNum);
            try {
                askForRestart();
            } catch (IllegalArgumentException e) {
                break;
            }
        }
    }

    public static String generateRandomNumber() {
        StringBuilder sb = new StringBuilder();
        List<Integer> randomNumList = new ArrayList<>();
        while (randomNumList.size() < NUMBER_LENGTH) {
            int randomNumber = Randoms.pickNumberInRange(MIN_NUMBER, MAX_NUMBER);
            if (!randomNumList.contains(randomNumber)) {
                randomNumList.add(randomNumber);
            }
        }
        for (Integer num : randomNumList) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static boolean playGame(String computerNum) {
        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            int strikeCount = 0;
            int ballCount = 0;

            String userNum = Console.readLine();

            if (userNum.length() != NUMBER_LENGTH) {
                throw new IllegalArgumentException("1에서 9까지로 이루어진 3자리 숫자를 입력해주세요.");
            }
            for (int i = 0; i < NUMBER_LENGTH; i++) {
                if (isStrike(computerNum, userNum, i)) {
                    strikeCount++;
                } else if (isBall(computerNum, userNum, i)) {
                    ballCount++;
                }
            }
            printGameHint(strikeCount, ballCount);
            if (isThreeStrike(strikeCount)) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                askForRestart();
            }
        }
    }

    public static void printGameHint(int strikeCount, int ballCount) {
        boolean hasBallOnly = ballCount > 0 && strikeCount == 0;
        boolean hasStrike = strikeCount > 0;
        boolean noHit = strikeCount == 0 && ballCount == 0;

        if (hasBallOnly) {
            System.out.println(ballCount + "볼");
        } else if (hasStrike) {
            if (ballCount > 0) {
                System.out.print(ballCount + "볼 ");
            }
            System.out.println(strikeCount + "스트라이크");
        } else if (noHit) {
            System.out.println("낫싱");
        }
    }


    private static boolean isStrike(String computerNum, String userNum, int index) {
        return computerNum.charAt(index) == userNum.charAt(index);
    }

    private static boolean isBall(String computerNum, String userNum, int userIndex) {
        char userChar = userNum.charAt(userIndex);
        int computerIndex = computerNum.indexOf(userChar);
        return computerIndex != -1 && computerIndex != userIndex;
    }

    private static boolean isThreeStrike(int strikeCount) {
        return strikeCount == NUMBER_LENGTH;
    }

    public static void askForRestart() {
        while (true) {
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String userInput = Console.readLine();
            switch (userInput) {
                case "1":
                    return;
                case "2":
                    throw new IllegalArgumentException("프로그램 종료");
                default:
                    System.out.println("잘못된 값을 입력했습니다");
                    throw new IllegalArgumentException("잘못된 값을 입력했습니다.");
            }
        }
    }

}
