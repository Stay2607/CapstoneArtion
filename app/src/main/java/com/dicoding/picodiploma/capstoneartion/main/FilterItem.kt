package com.dicoding.picodiploma.capstoneartion.main
//
//import android.widget.Filter
//
//class FilterItem : Filter {
//    var filterList: ArrayList<ModelPdf>
//
//    var adapterPdfAdmin: AdapterPdfAdmin
//
//    //Constructor
//    constructor(filterList: ArrayList<ModelPdf>, adapterPdfAdmin: AdapterPdfAdmin) {
//        this.filterList = filterList
//        this.adapterPdfAdmin = adapterPdfAdmin
//    }
//
//    override fun performFiltering(constraint: CharSequence?): FilterResults {
//        var constraint: CharSequence? = constraint //value to search
//        var result = FilterResults()
//        // value to be searched should not be null and not empty
//        if (constraint != null && constraint.isNotEmpty()) {
//            constraint = constraint.toString().lowercase()
//            var filteredModels = ArrayList<ModelPdf>
//            for (i in filterList.indices) {
//                // validate matching
//                if (filterList[i].title.lowercase().contains(constraint)) {
//                    // searched value is similiar to valud in the list, add to filtered list
//                    filteredModels.add(filterList[i])
//                }
//            }
//            result.count = filteredModels.size
//            result.values = filteredModels
//        } else {
//            // searched value is either null or empty, return all data
//            result.count = filteredModels.size
//            result.values = filterList
//        }
//        return result
//    }
//
//    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//        // apply
//    }
//}