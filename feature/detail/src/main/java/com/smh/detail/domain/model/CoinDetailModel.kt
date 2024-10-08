package com.smh.detail.domain.model

import com.smh.annotation.NXDateTimeExtension
import com.smh.annotation.NXStringToDate
import com.smh.annotation.changeFormatDate
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@NXDateTimeExtension
data class CoinDetailModel(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val blockTimeInMinutes: Int,
    val hashingAlgorithm: String,
    val description: String,
    val websiteUrl: String,
    val redditUrl: String,
    val marketCapRank: Int,
    val currentPrice: Double,
    val marketCap: Double,
    val totalVolume: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val marketCapChange24h: Double,
    val marketCapChangePercentage24h: Double,
    val sparkline: List<Double>,
    @NXStringToDate(originPattern = "yyyy-MM-dd'T'HH:mm:ss")
    val lastUpdated: String
) {
    val endDate: String get() = nx_date_lastUpdated.changeFormatDate(targetPattern = "dd-MM-yyyy")

    val startDate: String get() {
        if (nx_date_lastUpdated == null) return ""
        val calendar = Calendar.getInstance()
        calendar.time = nx_date_lastUpdated!!
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    companion object {
        val btc = CoinDetailModel(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://example.com/bitcoin.png",
            blockTimeInMinutes = 10,
            hashingAlgorithm = "SHA-256",
            description = "Bitcoin is a decentralized digital currency.",
            websiteUrl = "https://bitcoin.org",
            redditUrl = "https://reddit.com/r/bitcoin",
            marketCapRank = 1,
            currentPrice = 30000.00,
            marketCap = 600000000000.0,
            totalVolume = 40000000000.0,
            high24h = 32000.00,
            low24h = 29000.00,
            priceChange24h = -500.00,
            priceChangePercentage24h = -1.64,
            marketCapChange24h = -10000000.0,
            marketCapChangePercentage24h = -1.64,
            sparkline = listOf(
                58079.32469343483,
                58051.95310684931,
                58143.43107347114,
                58031.620969251846,
                58713.8547786192,
                58409.624456662576,
                58516.533287708495,
                58775.55485087848,
                58600.15872525685,
                58788.63907304515,
                58799.39040493895,
                58620.62628856922,
                58775.69500096371,
                58667.7987229473,
                58607.32407797006,
                58611.52229196427,
                58697.87243688947,
                59468.70435751196,
                59243.90724033287,
                59853.44861354578,
                59743.941788773656,
                59379.36393666037,
                59488.175182718296,
                60150.163597970684,
                60214.36763074621,
                59984.65762595794,
                60097.322837311534,
                60271.26869875073,
                60177.292569808065,
                59941.15613408041,
                60118.991511978995,
                59612.65548216775,
                59711.46094543786,
                60065.86230213515,
                60161.01663767256,
                60105.30674070386,
                60059.69256017659,
                60050.742527089096,
                59931.33689327525,
                60067.87504727605,
                61154.25049027309,
                60853.24309488443,
                60881.48885782499,
                61229.480385878865,
                61426.756346514296,
                62576.12941558953,
                62652.102418708375,
                62601.21539596413,
                62758.97597203408,
                62949.67638884755,
                62890.40632199661,
                62780.86549256509,
                62751.71811398488,
                62580.176000609245,
                62468.314406226025,
                62732.57146771641,
                62865.69466528791,
                63270.41832525062,
                63072.320176727175,
                63281.61714686876,
                63128.74570297137,
                63556.877053620046,
                63425.52176425553,
                63765.75236709564,
                63705.15201182238,
                64315.59239185009,
                64894.80923587094,
                64636.81716840933,
                64781.59633903313,
                64885.241268445745,
                64774.95557913305,
                64516.59579834891,
                63630.04229989184,
                62801.117747491146,
                62909.93699601887,
                62768.69454731729,
                63136.05202803013,
                63481.47969338322,
                63795.90436231302,
                63774.623843572,
                63179.658545696824,
                63898.0794376149,
                65235.73062171311,
                64435.20570024861,
                64566.00222661687,
                64780.92991307614,
                65144.970819556984,
                64772.756090466675,
                64566.06071322991,
                64698.987079558225,
                65178.21786410649,
                65621.07086132235,
                65663.96246100322,
                65724.43070784534,
                65727.38563188832,
                65866.76728301505,
                65691.81355407793,
                65580.27545006957,
                65316.587628053414,
                65203.83934198469,
                65209.820079650875,
                65077.63026247519,
                64838.2623882498,
                64734.50728516352,
                65021.51236363203,
                65024.15480192185,
                64689.74595192153,
                64340.502941883205,
                64246.41763737234,
                64576.21887222642,
                64591.54908451396,
                64523.47476463111,
                64352.04165931211,
                64288.46405518303,
                64101.49229397971,
                64543.149741454596,
                64667.536281704495,
                64638.949054802986,
                64785.32198038902,
                64639.0270103694,
                64817.12652407137,
                65005.47170329841,
                64714.2525694089,
                64955.2253984854,
                64720.07674355206,
                64619.52454366042,
                64667.49854157397,
                64897.466640545805,
                64654.91835236968,
                63771.94116284888,
                63676.04850620038,
                63940.49373036791,
                63590.66331234905,
                63590.63618283955,
                63604.750257719155,
                63747.28527433048,
                63705.35583839652,
                63981.370034196996,
                63975.96715356269,
                63565.528204915856,
                63809.22054036418,
                63838.349226737955,
                64192.30665062532,
                64130.08316868449,
                64221.280424641045,
                64183.36643211268,
                63824.805155061695,
                63869.76609325864,
                63672.044363448615,
                64091.17432236077,
                64043.14970164301,
                64397.689991955085,
                65163.527672846656,
                65658.89779660033,
                66380.8935458446,
                66302.72588518042,
                66571.72882588342,
                66829.52204964789,
                67265.13367947098,
                66943.57401197092,
                66917.57260080865,
                66726.52875458487,
                66659.36403104197,
                66484.87866164831,
                66298.72115095778,
                66623.85624202447,
                66566.6291426702,
                66642.59233018276
            ),
            lastUpdated = "2024-08-07T05:09:52.124Z"
        )
    }
}
