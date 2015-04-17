import ninja.siden.App;
import ninja.siden.Request;
import org.xnio.channels.StreamSourceChannel;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CapybaraController {

	private App app;

	public CapybaraController(App app) {
		this.app = app;
	}


	public void defineRoutes() {

		app.get(
				"/capybara",
				(req, res) -> {
					return "ウェヒヒwww";
				}
		);

		app.post(
				"/capybara",
				(req, res) -> {
					String body = body(req);
					return body;
				});
	}


	private  String body(Request req) {
		int size = (int) req.raw().getRequestContentLength();
		ByteBuffer buff = ByteBuffer.allocate(size);
		try (StreamSourceChannel reqChannel = req.raw().getRequestChannel()) {
			reqChannel.read(buff);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return new String(buff.array());

	}
}
