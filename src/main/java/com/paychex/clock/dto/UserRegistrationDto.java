package com.paychex.clock.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
