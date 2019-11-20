package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSignInRoute implements TemplateViewRoute {

	static final String CURRENT_USER_ATTR = "currentUser"
;
	static final String INVALID_SIGNIN_ATTR = "invalidSignIn";
	static final String INVALID_SIGNIN_MESSAGE = "Username or password is invalid.";

	static final String HOME_NAME = "home.ftl";
  static final String SIGNIN_NAME = "signin.ftl";

	private final GameCenter gameCenter;

	/**
	 * The constructor for the {@code GET /game} route handler.
	 *
	 * @param gameCenter
	 *    The {@link GameCenter} for the application.
	 */
	public PostSignInRoute(final GameCenter gameCenter) {
		// validation
		Objects.requireNonNull(gameCenter, "gameCenter must not be null");
		//
		this.gameCenter = gameCenter;
	}



  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();

		Map<String, String> credentials = new HashMap<>();
		credentials.put("user", request.queryParams("user"));
		credentials.put("password", request.queryParams("password"));

		try {
			String sessionID = gameCenter.login(credentials);
			vm.put(CURRENT_USER_ATTR, true);
			vm.put("title", "Home");
			return new ModelAndView(vm , HOME_NAME);
		} catch (Exception e) {
			vm.put("title", "Sign In");
			vm.put(INVALID_SIGNIN_ATTR, INVALID_SIGNIN_MESSAGE);
	    return new ModelAndView(vm , SIGNIN_NAME);
		}

  }

}