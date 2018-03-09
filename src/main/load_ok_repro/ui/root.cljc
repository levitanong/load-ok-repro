(ns load-ok-repro.ui.root
  (:require
    [fulcro.client.mutations :as m]
    [fulcro.client.data-fetch :as df]
    [fulcro.client.dom :as dom]
    [load-ok-repro.api.mutations :as api]
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.alpha.i18n :as i18n :refer [tr trf]]))

;; The main UI of your application

(defsc Root [this props]
  (dom/div nil "TODO"))
