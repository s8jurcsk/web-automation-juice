package support;

public class StringEditing {
  @SuppressWarnings("SpellCheckingInspection")
  public static String snakify(String str) {
    return str.toUpperCase().replaceAll("[^\\P{Punct}_]+", "").replaceAll(" ", "_");
  }
}
