package org.lineageos.jelly.suggestions;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Search suggestions provider for Qwant search engine.
 */
class QwantSuggestionProvider extends SuggestionProvider {
    QwantSuggestionProvider() {
        super("UTF-8");
    }

    @NonNull
    protected String createQueryUrl(@NonNull String query,
                                    @NonNull String language) {
        return "https://api.qwant.com/api/suggest?q=" + query + "&lang=" + language;
    }

    @Override
    protected void parseResults(@NonNull String content,
                                @NonNull ResultCallback callback) throws Exception {
        JSONObject jsonObject = new JSONObject(content);
        if (jsonObject.getString("status").equals("success")) {
            jsonObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int n = 0, size = jsonArray.length(); n < size; n++) {
                JSONObject object = jsonArray.getJSONObject(n);
                String suggestion = object.getString("value");
                if (!callback.addResult(suggestion)) break;
            }
        }
    }
}
