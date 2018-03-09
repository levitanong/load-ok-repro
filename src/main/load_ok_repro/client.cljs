(ns load-ok-repro.client
  (:require [fulcro.client :as fc]
            [fulcro.client.data-fetch :as df]
            [load-ok-repro.ui.root :as root]
            [fulcro.alpha.i18n :as i18n]
            [fulcro.client.network :as net]
            yahoo.intl-messageformat-with-locales))

(defn message-format [{:keys [::i18n/localized-format-string ::i18n/locale ::i18n/format-options]}]
  (let [locale-str (name locale)
        formatter  (js/IntlMessageFormat. localized-format-string locale-str)]
    (.format formatter (clj->js format-options))))

(defonce app (atom nil))

(defn mount []
  (reset! app (fc/mount @app root/Root "app")))

(defn start []
  (mount))

(defrecord CustomRemote []
  net/FulcroRemoteI
  (transmit [this {:keys [::net/edn ::net/ok-handler]}]
    (ok-handler {:transaction edn
                 :body        :derp}))
  (abort [this abort-id]))

(defn ^:export init []
  (reset! app (fc/new-fulcro-client
               :reconciler-options {:shared    {::i18n/message-formatter message-format}
                                    :shared-fn ::i18n/current-locale}
               :networking {:custom (map->CustomRemote {})}
               :started-callback (fn [{:keys [reconciler] :as app}]
                                   (df/load reconciler :testing nil
                                            {:remote :custom}))))
  (start))

