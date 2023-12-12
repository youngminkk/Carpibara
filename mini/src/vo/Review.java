package vo;


import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@SuppressWarnings("serial")
@Getter
@Setter
@Builder
public class Review implements Serializable{
	private final String inputName;//이름
	private final String inputText	;//후기내용
	private final int inputStar;//별점
	private final String inputId;
	private final int no;
	
}