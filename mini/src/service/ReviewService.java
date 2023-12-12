package service;

import java.util.List;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.Utils;
import vo.Review;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReviewService {
	private static ReviewService reviewService = new ReviewService();
	Scanner scanner = new Scanner(System.in);

	
	private List<Review> reviews = Utils.load("reviews");

	public void reviewSwitch() {
		System.out.println("\n[2.리뷰]");
		
		while (true) {
			
			try {
				switch (Utils.nextInt("1.후기작성 ㅣ 2.후기삭제 ㅣ 3.후기수정 \n" + "4.후기조회 ㅣ 5.돌아가기")) {
				case 1: // 후기작성
					write();
					break;
				case 2: // 후기삭제
					remove();
					break;
				case 3: // 후기수정
					modify();
					break;
				case 4: // 후기조회
					System.out.println("\n(2-4.후기조회)");
					if (reviews.size() == 0) {
						System.out.println("현재 등록된 후기글이 없습니다.");
					}
					for (int i = 0; i < reviews.size(); i++) {
						list(reviews.get(i));
					}
					break;
				case 5:
					System.out.println("=========================================");
					TaxiCall.getinstance().call();
					return;
				default: // 돌아가기
					System.out.println("지정된 메뉴 번호를 입력해주세요.");
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력하세요");
			}
		}
	}

	void write() { // 후기작성
		System.out.println("해당 서비스 별점 [1점~5점]");
		int inputStar = scanner.nextInt();
		scanner.nextLine();
		while (inputStar > 5 || 0 > inputStar) {
			System.out.println("별점은 1점부터 5점까지 입력 가능합니다.");
			inputStar = scanner.nextInt();
		}

		System.out.println("이용후기 내용을 입력하세요.");
		String inputText = scanner.nextLine();
		String id = UserService.getInstance().getLoginUser().getId();
		insert(Review.builder().inputStar(inputStar).inputText(inputText).inputId(id).build());
		Utils.save();
	}

	private int insert(Review review) {
		int no = reviews.size() == 0 ? 1 : reviews.get(reviews.size() - 1).getNo() + 1;
		Review review2 = Review.builder().inputStar(review.getInputStar()).inputText(review.getInputText())
				.inputId(review.getInputId()).no(no).build();
		return reviews.add(review2) ? 1 : 0;
	}

	public void list(Review review) {
		for (Review rv : reviews) {
			if (rv == null) {
				System.out.println("현재 등록된 리뷰가 없습니다.");
			}
		}
		System.out.println("[" + review.getNo() + "] 아이디 : " + UserService.getInstance().getLoginUser().getId());
		switch (review.getInputStar()) {
		case 1:
			System.out.println("별점 : ★");
			break;
		case 2:
			System.out.println("별점 : ★ ★");
			break;
		case 3:
			System.out.println("별점 : ★ ★ ★");
			break;
		case 4:
			System.out.println("별점 : ★ ★ ★ ★");
			break;
		case 5:
			System.out.println("별점 : ★ ★ ★ ★ ★");
			break;

		default:
			break;
		}
		System.out.println("후기내용 : " + review.getInputText());
		System.out.println("-----------------------------------");
	}

	Review findReviewer(int no) { // 후기글 넘버로 찾기
		for (int i = 0; i < reviews.size(); i++) {
			if (no == reviews.get(i).getNo()) {
				return reviews.get(i);
			}
		}
		return null;
	}

	public void modify() {// 후기글 수정
		System.out.println("\n(2-3.후기수정)");
		System.out.println("수정할 후기글 번호입력");
		int no2 = scanner.nextInt();
		Review r = findReviewer(no2);

		if (r == null) { // 찾는 후기글이 없을때
			System.out.println("찾으려는 후기글이 없습니다.");
			return;
		}
		if (UserService.getInstance().getLoginUser().getId() != r.getInputId()) {
			// 접속중인 ID와 수정하려는 작성자의 ID가 일치하지 않을때
			System.out.println("다른 작성자가 작성한 후기 입니다.");
			return;
		}
		String inputName2 = UserService.getInstance().getLoginUser().getId(); // 작성자의 ID
		System.out.println("해당 서비스 별점 [1점~5점]");
		int inputStar2 = scanner.nextInt();
		scanner.nextLine();
		while (inputStar2 > 5 || 0 > inputStar2) {
			System.out.println("별점은 1점부터 5점까지 입력 가능합니다.");
			inputStar2 = scanner.nextInt();
		}
		System.out.println("후기글을 작성해주세요.");
		String inputText2 = scanner.nextLine();

		update(Review.builder().inputStar(inputStar2).inputText(inputText2).inputId(inputName2).no(no2).build());
		Utils.save();
	}

	private int update(Review review) {
		int idx = reviews.indexOf(selectOne(review.getNo()));
		return reviews.set(idx, review) != null ? 1 : 0;
	}

	private Review selectOne(int bno) {
		Review review = null;
		for (Review b : reviews) {
			if (bno == b.getNo()) {
				review = b;
			}
		}
		return review;
	}

	public void remove() { // 후기 삭제
		System.out.println("\n(2-2.후기삭제)");
		System.out.println("삭제할 후기글 번호 입력");
		int remove = scanner.nextInt();
		Review r = findReviewer(remove);
		if (r == null) { // 찾는 글이 없을때
			System.out.println("없는 후기입니다.");
			return;
		}
		if (UserService.getInstance().getLoginUser().getId() != r.getInputId()) {
			// 접속중인 ID와 삭제하고자 하는 글의 작성자가 다를때
			System.out.println("다른 작성자가 작성한 후기 입니다.");
			return;
		} // 삭제
		System.out.println(remove + "번 후기가 삭제되었습니다.");
		reviews.remove(r);
		Utils.save();
	}

	public static ReviewService getInstance() {
		return reviewService;
	}
}
