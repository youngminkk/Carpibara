package vo;



import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@SuppressWarnings("serial")
@Builder
@AllArgsConstructor
public class User implements Serializable{
	private final String name;
	private final String id;
	private final String pw;
	int card;
	int cash;
	
	
}