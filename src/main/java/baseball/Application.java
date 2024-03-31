package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // 게임 실행
        while (true) {
            // 게임 설정 및 시작
            String computerNum = setting();
            // 게임 실행
            boolean continueGame = game(computerNum);
            // 게임 종료 조건 검사
            if (!continueGame) {
                break;
            }
        }
    }

    //게임 초기 설정(컴퓨터가 정한 랜덤값)
    public static String setting() {
        StringBuilder sb = new StringBuilder();
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        for (Integer num : computer) {
            sb.append(num);
        }
        System.out.println("숫자 야구 게임을 시작합니다.");
        return sb.toString();
    }


    public static boolean game(String computerNum) {
        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            //스트라이크 개수
            int strikeCount = 0;
            //볼 개수
            int ballCount = 0;

            //유저 입력
            String userNum = Console.readLine();

            //길이가 3이 아니면 IllegalArgumentException 발생 시키고 프로그램 종료
            if(userNum.length()!=3){
                throw new IllegalArgumentException();
            }
            //유저의 값과 컴퓨터의 값을 비교
            for (int i = 0; i < 3; i++) {
                if (userNum.charAt(i) == computerNum.charAt(i)) {
                    strikeCount++;
                } else if (computerNum.contains(String.valueOf(userNum.charAt(i)))) {
                    ballCount++;
                }
            }

            //모두 맞출 시 1 또는 2 입력, 1 입력 시 게임 다시 진행, 2 입력 시 게임 종료
            if (strikeCount == 3) {
                System.out.println(strikeCount + "스트라이크");
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                int userChoose = Integer.parseInt(Console.readLine());
                return userChoose == 1;
            }

            //볼 출력
            if (ballCount > 0 && strikeCount ==0) {
                System.out.println(ballCount + "볼 ");
            }else if(ballCount > 0 && strikeCount !=0){
                System.out.print(ballCount + "볼 ");
            }

            //스트라이크 출력
            if (strikeCount > 0 && strikeCount < 3) {
                System.out.println(strikeCount + "스트라이크");
            }

            // 하나도 못 맞출 시 낫싱 출력
            if (strikeCount == 0 && ballCount == 0) {
                System.out.println("낫싱");
            }
        }
    }
}