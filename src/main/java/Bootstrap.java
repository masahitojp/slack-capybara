import ninja.siden.App;
import ninja.siden.Renderer;
import ninja.siden.Stoppable;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Token;
import org.boon.json.JsonFactory;

public class Bootstrap {
	public static void kuromoji() {
		final Tokenizer tokenizer = Tokenizer.builder().build();
		for (final Token token : tokenizer.tokenize("こんにちは、ぼく、ドラえもんです")) {
			System.out.println(token.getSurfaceForm() + "\t" + token.getAllFeatures());
		}
	}

	public static void main(final String[] args) {
		kuromoji();
		final App app = new App();
		new CapybaraController(app).defineRoutes();
		final Stoppable stoppable = app.listen();
		java.lang.Runtime.getRuntime().addShutdownHook(
				new Thread() {
					@Override
					public void run() {
						stoppable.stop();
						Bootstrap.stop();
					}
				}
		);
	}

	public static void stop() {
		System.out.println("stop");
	}
}
