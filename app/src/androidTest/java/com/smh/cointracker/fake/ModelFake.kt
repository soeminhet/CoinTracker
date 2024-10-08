package com.smh.cointracker.fake

import com.smh.detail.domain.model.CoinDetailModel
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.home.ui.model.StatisticModel

internal val btc = CoinModel(
    id = "bitcoin",
    rank = 1,
    symbol = "BTC",
    image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png",
    name = "BITCOIN",
    price = 56630.00,
    priceChangePercentage = 1.18,
    holdingAmount = null
)

internal val eth = CoinModel(
    id = "ethereum",
    rank = 2,
    symbol = "ETH",
    image = "https://coin-images.coingecko.com/coins/images/1/large/ethereum.png",
    name = "ETHEREUM",
    price = 12333.00,
    priceChangePercentage = -1.18,
    holdingAmount = null
)

internal val usdt = CoinModel(
    id = "usdt",
    rank = 3,
    symbol = "USDT",
    image = "https://coin-images.coingecko.com/coins/images/1/large/usdt.png",
    name = "USDT",
    price = 0.99,
    priceChangePercentage = 1.90,
    holdingAmount = null
)

internal val global = GlobalModel(
    marketCapChangePercentage = 0.55,
    marketCap = "1.2TR",
    btcDominance = "40%",
    volume = "200Bn"
)

internal val coinDetailModel = CoinDetailModel(
    id = "bitcoin",
    symbol = "BTC",
    name = "Bitcoin",
    image = "https://coin-images.coingecko.com/coins/images/1/small/bitcoin.png?1696501400",
    blockTimeInMinutes = 10,
    hashingAlgorithm = "SHA-256",
    description = "Bitcoin is the first successful internet money based on peer-to-peer technology; whereby no central bank or authority is involved in the transaction and production of the Bitcoin currency. It was created by an anonymous individual/group under the name, Satoshi Nakamoto. The source code is available publicly as an open source project, anybody can look at it and be part of the developmental process.\r\n\r\nBitcoin is changing the way we see money as we speak. The idea was to produce a means of exchange, independent of any central authority, that could be transferred electronically in a secure, verifiable and immutable way. It is a decentralized peer-to-peer internet currency making mobile payment easy, very low transaction fees, protects your identity, and it works anywhere all the time with no central authority and banks.\r\n\r\nBitcoin is designed to have only 21 million BTC ever created, thus making it a deflationary currency. Bitcoin uses the <a href=\"https://www.coingecko.com/en?hashing_algorithm=SHA-256\">SHA-256</a> hashing algorithm with an average transaction confirmation time of 10 minutes. Miners today are mining Bitcoin using ASIC chip dedicated to only mining Bitcoin, and the hash rate has shot up to peta hashes.\r\n\r\nBeing the first successful online cryptography currency, Bitcoin has inspired other alternative currencies such as <a href=\"https://www.coingecko.com/en/coins/litecoin\">Litecoin</a>, <a href=\"https://www.coingecko.com/en/coins/peercoin\">Peercoin</a>, <a href=\"https://www.coingecko.com/en/coins/primecoin\">Primecoin</a>, and so on.\r\n\r\nThe cryptocurrency then took off with the innovation of the turing-complete smart contract by <a href=\"https://www.coingecko.com/en/coins/ethereum\">Ethereum</a> which led to the development of other amazing projects such as <a href=\"https://www.coingecko.com/en/coins/eos\">EOS</a>, <a href=\"https://www.coingecko.com/en/coins/tron\">Tron</a>, and even crypto-collectibles such as <a href=\"https://www.coingecko.com/buzz/ethereum-still-king-dapps-cryptokitties-need-1-billion-on-eos\">CryptoKitties</a>.",
    websiteUrl = "http://www.bitcoin.org",
    redditUrl = "https://www.reddit.com/r/Bitcoin/",
    marketCapRank = 1,
    currentPrice = 57240.0,
    marketCap = 1128993186464.0,
    totalVolume = 51824659990.0,
    high24h = 57265.0,
    low24h = 54578.0,
    priceChange24h = 1665.0,
    priceChangePercentage24h = 2.99527,
    marketCapChange24h = 32768439654.0,
    marketCapChangePercentage24h = 2.98921,
    sparkline = listOf(
        66191.9479518304, 66308.9825510405, 66180.9506805394, 66140.3141691204, 65811.8670421552, 65671.0733563504, 66268.7279987628, 66385.0927645415, 66249.1302833131, 66280.1131315081, 66101.8598271662, 66053.5372183455, 66071.2030577151, 66274.4173901326, 66654.0671668784, 66378.9881926607, 66481.0149095536, 66405.4510744334, 66211.7038457068, 66342.2801844527, 64700.2993615703, 64666.3291991292, 64765.9638231031, 64730.2342570455, 64582.7609188144, 64567.411201866, 64160.6993344809, 64097.7149305709, 63834.8006349477, 63721.9335515343, 64274.3519293492, 64420.621183666, 64389.394456862, 64481.8067440323, 64378.2181793004, 64455.2956640067, 64775.5764047226, 64863.7342431434, 64502.9584493431, 63653.7320476495, 63039.6132192987, 62632.7159202967, 63009.6421598638, 63114.7969380272, 63470.0080933854, 64739.0490063712, 65192.9220893696, 65113.9681462809, 65357.5296076744, 64651.8090674225, 64591.1384261077, 64296.1110028918, 64570.2627660214, 64261.640686157, 64023.1618293619, 64285.2686403649, 64174.8204535312, 64419.5741218134, 64343.2011310257, 64639.7205326109, 64604.6161112339, 64577.6996750468, 64454.8998717148, 63643.178705887, 63420.8651187667, 63362.6861096512, 63045.1049546791, 62799.9837915646, 62194.5803116804, 62022.8389318586, 62033.3724382321, 61613.9517440725, 61432.842683726, 60704.4438271143, 61474.3143391424, 61565.2573967029, 61780.3219750787, 61630.623751722, 61659.7636440961, 61708.6495825108, 61643.4154542589, 61533.5559852543, 61714.6743558425, 61795.804970759, 61938.8369512601, 61898.4285716159, 62043.3430875248, 61751.9204750683, 60743.1133085195, 60640.5117713752, 60576.842880622, 60237.7088921197, 60437.4375867446, 60315.1330184938, 60600.733873835, 60621.4080121901, 60746.8704427933, 60559.2301288814, 60835.4196800715, 60846.0455362433, 60684.3810906795, 60541.5038241344, 60769.0519091393, 60747.6870150096, 60366.1335543977, 60490.2594570368, 60686.3246386217, 60920.2501529709, 60795.2726903692, 60941.9800636082, 60798.024342322, 59349.2000675441, 58959.6410009881, 58147.3643865789, 57866.503799085, 58969.1118142175, 59501.1373004277, 58984.0533528357, 59098.092518146, 58536.7664270569, 57775.1630372958, 54702.1510989991, 54384.5083857118, 54425.2369642231, 54193.8364967853, 52590.3464990559, 50942.4657927813, 52777.8772817989, 52675.1280799722, 52779.791616763, 52610.725064062, 51544.978580686, 50793.7696849884, 49781.9250782462, 53604.8121598922, 54385.2093861371, 55156.9872415576, 54562.708961745, 53827.1338149523, 53020.4847839457, 53931.393370234, 54660.3882968328, 55059.3860065648, 54997.0132585007, 55700.3443756142, 56107.3007692464, 55723.3941781377, 55595.7523100551, 55677.185757782, 55516.0804852546, 55823.8047914747, 55800.9061132436, 55622.3433436704, 54908.9199776623, 55163.8405431353, 55031.53613391, 55227.072415635, 54822.4181069483, 56335.8136823691, 55957.7178801147, 56370.2872577928, 56943.2216474051, 56903.2131341663, 56582.6600826322, 56500.0513676601, 56561.452220746, 56924.6160907848, 56179.9301585033
    ),
    lastUpdated = "2024-08-07T05:09:52.124Z"
)