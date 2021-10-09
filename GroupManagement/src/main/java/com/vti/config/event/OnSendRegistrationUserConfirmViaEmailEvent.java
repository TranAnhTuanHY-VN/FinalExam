package com.vti.config.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnSendRegistrationUserConfirmViaEmailEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String email;

	public OnSendRegistrationUserConfirmViaEmailEvent(String email) {
		super(email);
		this.email = email;
	}

}
