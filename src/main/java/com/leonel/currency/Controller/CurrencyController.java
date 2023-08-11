package com.leonel.currency.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Entity.Request;
import com.leonel.currency.Service.CurrencyServiceIMPL.CurrencyServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    @Value("${api.currency.uriLatest}")
    String uri;
    @Value("${api.currency.apikey}")
    String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyServiceIMPL currency;

    public CurrencyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String json = "{\n" +
            "  \"meta\": {\n" +
            "    \"last_updated_at\": \"2023-08-08T23:59:59Z\"\n" +
            "  },\n" +
            "  \"data\": {\n" +
            "    \"ADA\": {\n" +
            "      \"code\": \"ADA\",\n" +
            "      \"value\": 10.999999\n" +
            "    },\n" +
            "    \"AED\": {\n" +
            "      \"code\": \"AED\",\n" +
            "      \"value\": 3.6718105602\n" +
            "    },\n" +
            "    \"AFN\": {\n" +
            "      \"code\": \"AFN\",\n" +
            "      \"value\": 84.66218286\n" +
            "    },\n" +
            "    \"ALL\": {\n" +
            "      \"code\": \"ALL\",\n" +
            "      \"value\": 94.1271779798\n" +
            "    },\n" +
            "    \"AMD\": {\n" +
            "      \"code\": \"AMD\",\n" +
            "      \"value\": 384.5554904689\n" +
            "    },\n" +
            "    \"ANG\": {\n" +
            "      \"code\": \"ANG\",\n" +
            "      \"value\": 1.7862602414\n" +
            "    },\n" +
            "    \"AOA\": {\n" +
            "      \"code\": \"AOA\",\n" +
            "      \"value\": 823.5558170734\n" +
            "    },\n" +
            "    \"ARB\": {\n" +
            "      \"code\": \"ARB\",\n" +
            "      \"value\": 0.8604070992\n" +
            "    },\n" +
            "    \"ARS\": {\n" +
            "      \"code\": \"ARS\",\n" +
            "      \"value\": 284.0845904689\n" +
            "    },\n" +
            "    \"AUD\": {\n" +
            "      \"code\": \"AUD\",\n" +
            "      \"value\": 1.5306702503\n" +
            "    },\n" +
            "    \"AVAX\": {\n" +
            "      \"code\": \"AVAX\",\n" +
            "      \"value\": 0.0786470062\n" +
            "    },\n" +
            "    \"AWG\": {\n" +
            "      \"code\": \"AWG\",\n" +
            "      \"value\": 1.79\n" +
            "    },\n" +
            "    \"AZN\": {\n" +
            "      \"code\": \"AZN\",\n" +
            "      \"value\": 1.7\n" +
            "    },\n" +
            "    \"BAM\": {\n" +
            "      \"code\": \"BAM\",\n" +
            "      \"value\": 1.7882103197\n" +
            "    },\n" +
            "    \"BBD\": {\n" +
            "      \"code\": \"BBD\",\n" +
            "      \"value\": 2\n" +
            "    },\n" +
            "    \"BDT\": {\n" +
            "      \"code\": \"BDT\",\n" +
            "      \"value\": 109.5332764793\n" +
            "    },\n" +
            "    \"BGN\": {\n" +
            "      \"code\": \"BGN\",\n" +
            "      \"value\": 1.7799903076\n" +
            "    },\n" +
            "    \"BHD\": {\n" +
            "      \"code\": \"BHD\",\n" +
            "      \"value\": 0.376\n" +
            "    },\n" +
            "    \"BIF\": {\n" +
            "      \"code\": \"BIF\",\n" +
            "      \"value\": 2826.7819230011\n" +
            "    },\n" +
            "    \"BMD\": {\n" +
            "      \"code\": \"BMD\",\n" +
            "      \"value\": 1\n" +
            "    },\n" +
            "    \"BNB\": {\n" +
            "      \"code\": \"BNB\",\n" +
            "      \"value\": 0.0040409667\n" +
            "    },\n" +
            "    \"BND\": {\n" +
            "      \"code\": \"BND\",\n" +
            "      \"value\": 1.3470701582\n" +
            "    },\n" +
            "    \"BOB\": {\n" +
            "      \"code\": \"BOB\",\n" +
            "      \"value\": 6.924571032\n" +
            "    },\n" +
            "    \"BRL\": {\n" +
            "      \"code\": \"BRL\",\n" +
            "      \"value\": 4.8989906588\n" +
            "    },\n" +
            "    \"BSD\": {\n" +
            "      \"code\": \"BSD\",\n" +
            "      \"value\": 1\n" +
            "    },\n" +
            "    \"BTC\": {\n" +
            "      \"code\": \"BTC\",\n" +
            "      \"value\": 0.0000335174\n" +
            "    },\n" +
            "    \"BTN\": {\n" +
            "      \"code\": \"BTN\",\n" +
            "      \"value\": 82.7385854617\n" +
            "    },\n" +
            "    \"BUSD\": {\n" +
            "      \"code\": \"BUSD\",\n" +
            "      \"value\": 0.9973719323\n" +
            "    },\n" +
            "    \"BWP\": {\n" +
            "      \"code\": \"BWP\",\n" +
            "      \"value\": 13.4764614316\n" +
            "    },\n" +
            "    \"BYN\": {\n" +
            "      \"code\": \"BYN\",\n" +
            "      \"value\": 2.5003358722\n" +
            "    },\n" +
            "    \"BYR\": {\n" +
            "      \"code\": \"BYR\",\n" +
            "      \"value\": 25003.355647361\n" +
            "    },\n" +
            "    \"BZD\": {\n" +
            "      \"code\": \"BZD\",\n" +
            "      \"value\": 2\n" +
            "    },\n" +
            "    \"CAD\": {\n" +
            "      \"code\": \"CAD\",\n" +
            "      \"value\": 1.3424301825\n" +
            "    },\n" +
            "    \"CDF\": {\n" +
            "      \"code\": \"CDF\",\n" +
            "      \"value\": 2482.1416465688\n" +
            "    },\n" +
            "    \"CHF\": {\n" +
            "      \"code\": \"CHF\",\n" +
            "      \"value\": 0.8755000895\n" +
            "    },\n" +
            "    \"CLF\": {\n" +
            "      \"code\": \"CLF\",\n" +
            "      \"value\": 0.0235300044\n" +
            "    },\n" +
            "    \"CLP\": {\n" +
            "      \"code\": \"CLP\",\n" +
            "      \"value\": 862.0828888076\n" +
            "    },\n" +
            "    \"CNY\": {\n" +
            "      \"code\": \"CNY\",\n" +
            "      \"value\": 7.215320915\n" +
            "    },\n" +
            "    \"COP\": {\n" +
            "      \"code\": \"COP\",\n" +
            "      \"value\": 4077.6355916005\n" +
            "    },\n" +
            "    \"CRC\": {\n" +
            "      \"code\": \"CRC\",\n" +
            "      \"value\": 541.4001998301\n" +
            "    },\n" +
            "    \"CUC\": {\n" +
            "      \"code\": \"CUC\",\n" +
            "      \"value\": 1\n" +
            "    },\n" +
            "    \"CUP\": {\n" +
            "      \"code\": \"CUP\",\n" +
            "      \"value\": 24\n" +
            "    },\n" +
            "    \"CVE\": {\n" +
            "      \"code\": \"CVE\",\n" +
            "      \"value\": 100.6584854187\n" +
            "    },\n" +
            "    \"CZK\": {\n" +
            "      \"code\": \"CZK\",\n" +
            "      \"value\": 22.1020741236\n" +
            "    },\n" +
            "    \"DAI\": {\n" +
            "      \"code\": \"DAI\",\n" +
            "      \"value\": 0.9987462478\n" +
            "    },\n" +
            "    \"DJF\": {\n" +
            "      \"code\": \"DJF\",\n" +
            "      \"value\": 177.721\n" +
            "    },\n" +
            "    \"DKK\": {\n" +
            "      \"code\": \"DKK\",\n" +
            "      \"value\": 6.7975913486\n" +
            "    },\n" +
            "    \"DOP\": {\n" +
            "      \"code\": \"DOP\",\n" +
            "      \"value\": 56.5310063645\n" +
            "    },\n" +
            "    \"DOT\": {\n" +
            "      \"code\": \"DOT\",\n" +
            "      \"value\": 0.1972359287\n" +
            "    },\n" +
            "    \"DZD\": {\n" +
            "      \"code\": \"DZD\",\n" +
            "      \"value\": 136.1647454755\n" +
            "    },\n" +
            "    \"EGP\": {\n" +
            "      \"code\": \"EGP\",\n" +
            "      \"value\": 30.896114795\n" +
            "    },\n" +
            "    \"ERN\": {\n" +
            "      \"code\": \"ERN\",\n" +
            "      \"value\": 15\n" +
            "    },\n" +
            "    \"ETB\": {\n" +
            "      \"code\": \"ETB\",\n" +
            "      \"value\": 55.0528707924\n" +
            "    },\n" +
            "    \"ETH\": {\n" +
            "      \"code\": \"ETH\",\n" +
            "      \"value\": 0.0005375757\n" +
            "    },\n" +
            "    \"EUR\": {\n" +
            "      \"code\": \"EUR\",\n" +
            "      \"value\": 0.9124001388\n" +
            "    },\n" +
            "    \"FJD\": {\n" +
            "      \"code\": \"FJD\",\n" +
            "      \"value\": 2.2401802356\n" +
            "    },\n" +
            "    \"FKP\": {\n" +
            "      \"code\": \"FKP\",\n" +
            "      \"value\": 0.7848132024\n" +
            "    },\n" +
            "    \"GBP\": {\n" +
            "      \"code\": \"GBP\",\n" +
            "      \"value\": 0.7848201457\n" +
            "    },\n" +
            "    \"GEL\": {\n" +
            "      \"code\": \"GEL\",\n" +
            "      \"value\": 2.6130204145\n" +
            "    },\n" +
            "    \"GGP\": {\n" +
            "      \"code\": \"GGP\",\n" +
            "      \"value\": 0.7848131721\n" +
            "    },\n" +
            "    \"GHS\": {\n" +
            "      \"code\": \"GHS\",\n" +
            "      \"value\": 11.0029317079\n" +
            "    },\n" +
            "    \"GIP\": {\n" +
            "      \"code\": \"GIP\",\n" +
            "      \"value\": 0.784813377\n" +
            "    },\n" +
            "    \"GMD\": {\n" +
            "      \"code\": \"GMD\",\n" +
            "      \"value\": 58.1351914106\n" +
            "    },\n" +
            "    \"GNF\": {\n" +
            "      \"code\": \"GNF\",\n" +
            "      \"value\": 8564.6351976915\n" +
            "    },\n" +
            "    \"GTQ\": {\n" +
            "      \"code\": \"GTQ\",\n" +
            "      \"value\": 7.8538215593\n" +
            "    },\n" +
            "    \"GYD\": {\n" +
            "      \"code\": \"GYD\",\n" +
            "      \"value\": 209.1683524847\n" +
            "    },\n" +
            "    \"HKD\": {\n" +
            "      \"code\": \"HKD\",\n" +
            "      \"value\": 7.8121612997\n" +
            "    },\n" +
            "    \"HNL\": {\n" +
            "      \"code\": \"HNL\",\n" +
            "      \"value\": 24.5886340854\n" +
            "    },\n" +
            "    \"HRK\": {\n" +
            "      \"code\": \"HRK\",\n" +
            "      \"value\": 7.0420511222\n" +
            "    },\n" +
            "    \"HTG\": {\n" +
            "      \"code\": \"HTG\",\n" +
            "      \"value\": 137.4926578326\n" +
            "    },\n" +
            "    \"HUF\": {\n" +
            "      \"code\": \"HUF\",\n" +
            "      \"value\": 355.6606021912\n" +
            "    },\n" +
            "    \"IDR\": {\n" +
            "      \"code\": \"IDR\",\n" +
            "      \"value\": 15199.623101171\n" +
            "    },\n" +
            "    \"ILS\": {\n" +
            "      \"code\": \"ILS\",\n" +
            "      \"value\": 3.7259105443\n" +
            "    },\n" +
            "    \"IMP\": {\n" +
            "      \"code\": \"IMP\",\n" +
            "      \"value\": 0.7848135128\n" +
            "    },\n" +
            "    \"INR\": {\n" +
            "      \"code\": \"INR\",\n" +
            "      \"value\": 82.842173998\n" +
            "    },\n" +
            "    \"IQD\": {\n" +
            "      \"code\": \"IQD\",\n" +
            "      \"value\": 1307.3374829127\n" +
            "    },\n" +
            "    \"IRR\": {\n" +
            "      \"code\": \"IRR\",\n" +
            "      \"value\": 41998.7969923\n" +
            "    },\n" +
            "    \"ISK\": {\n" +
            "      \"code\": \"ISK\",\n" +
            "      \"value\": 132.3214516798\n" +
            "    },\n" +
            "    \"JEP\": {\n" +
            "      \"code\": \"JEP\",\n" +
            "      \"value\": 0.7848136172\n" +
            "    },\n" +
            "    \"JMD\": {\n" +
            "      \"code\": \"JMD\",\n" +
            "      \"value\": 153.6757524024\n" +
            "    },\n" +
            "    \"JOD\": {\n" +
            "      \"code\": \"JOD\",\n" +
            "      \"value\": 0.71\n" +
            "    },\n" +
            "    \"JPY\": {\n" +
            "      \"code\": \"JPY\",\n" +
            "      \"value\": 143.2501427918\n" +
            "    },\n" +
            "    \"KES\": {\n" +
            "      \"code\": \"KES\",\n" +
            "      \"value\": 142.9934982699\n" +
            "    },\n" +
            "    \"KGS\": {\n" +
            "      \"code\": \"KGS\",\n" +
            "      \"value\": 87.6955356293\n" +
            "    },\n" +
            "    \"KHR\": {\n" +
            "      \"code\": \"KHR\",\n" +
            "      \"value\": 4128.170485668\n" +
            "    },\n" +
            "    \"KMF\": {\n" +
            "      \"code\": \"KMF\",\n" +
            "      \"value\": 449.6488402541\n" +
            "    },\n" +
            "    \"KPW\": {\n" +
            "      \"code\": \"KPW\",\n" +
            "      \"value\": 900.0156186557\n" +
            "    },\n" +
            "    \"KRW\": {\n" +
            "      \"code\": \"KRW\",\n" +
            "      \"value\": 1315.0051958871\n" +
            "    },\n" +
            "    \"KWD\": {\n" +
            "      \"code\": \"KWD\",\n" +
            "      \"value\": 0.3077600493\n" +
            "    },\n" +
            "    \"KYD\": {\n" +
            "      \"code\": \"KYD\",\n" +
            "      \"value\": 0.83333\n" +
            "    },\n" +
            "    \"KZT\": {\n" +
            "      \"code\": \"KZT\",\n" +
            "      \"value\": 445.6548907122\n" +
            "    },\n" +
            "    \"LAK\": {\n" +
            "      \"code\": \"LAK\",\n" +
            "      \"value\": 19354.406846806\n" +
            "    },\n" +
            "    \"LBP\": {\n" +
            "      \"code\": \"LBP\",\n" +
            "      \"value\": 15009.06760454\n" +
            "    },\n" +
            "    \"LKR\": {\n" +
            "      \"code\": \"LKR\",\n" +
            "      \"value\": 318.2079733131\n" +
            "    },\n" +
            "    \"LRD\": {\n" +
            "      \"code\": \"LRD\",\n" +
            "      \"value\": 186.730221773\n" +
            "    },\n" +
            "    \"LSL\": {\n" +
            "      \"code\": \"LSL\",\n" +
            "      \"value\": 18.6979835248\n" +
            "    },\n" +
            "    \"LTC\": {\n" +
            "      \"code\": \"LTC\",\n" +
            "      \"value\": 0.011906077\n" +
            "    },\n" +
            "    \"LTL\": {\n" +
            "      \"code\": \"LTL\",\n" +
            "      \"value\": 3.1507254644\n" +
            "    },\n" +
            "    \"LVL\": {\n" +
            "      \"code\": \"LVL\",\n" +
            "      \"value\": 0.6413141543\n" +
            "    },\n" +
            "    \"LYD\": {\n" +
            "      \"code\": \"LYD\",\n" +
            "      \"value\": 4.7886005402\n" +
            "    },\n" +
            "    \"MAD\": {\n" +
            "      \"code\": \"MAD\",\n" +
            "      \"value\": 9.737631451\n" +
            "    },\n" +
            "    \"MATIC\": {\n" +
            "      \"code\": \"MATIC\",\n" +
            "      \"value\": 1.4559166258\n" +
            "    },\n" +
            "    \"MDL\": {\n" +
            "      \"code\": \"MDL\",\n" +
            "      \"value\": 17.5643819789\n" +
            "    },\n" +
            "    \"MGA\": {\n" +
            "      \"code\": \"MGA\",\n" +
            "      \"value\": 4450.5918630065\n" +
            "    },\n" +
            "    \"MKD\": {\n" +
            "      \"code\": \"MKD\",\n" +
            "      \"value\": 56.049748167\n" +
            "    },\n" +
            "    \"MMK\": {\n" +
            "      \"code\": \"MMK\",\n" +
            "      \"value\": 2094.9247768905\n" +
            "    },\n" +
            "    \"MNT\": {\n" +
            "      \"code\": \"MNT\",\n" +
            "      \"value\": 3459.5768112532\n" +
            "    },\n" +
            "    \"MOP\": {\n" +
            "      \"code\": \"MOP\",\n" +
            "      \"value\": 8.0304012487\n" +
            "    },\n" +
            "    \"MRO\": {\n" +
            "      \"code\": \"MRO\",\n" +
            "      \"value\": 356.999828\n" +
            "    },\n" +
            "    \"MUR\": {\n" +
            "      \"code\": \"MUR\",\n" +
            "      \"value\": 45.7396679214\n" +
            "    },\n" +
            "    \"MVR\": {\n" +
            "      \"code\": \"MVR\",\n" +
            "      \"value\": 15.4527328681\n" +
            "    },\n" +
            "    \"MWK\": {\n" +
            "      \"code\": \"MWK\",\n" +
            "      \"value\": 1081.48061486\n" +
            "    },\n" +
            "    \"MXN\": {\n" +
            "      \"code\": \"MXN\",\n" +
            "      \"value\": 17.1190722575\n" +
            "    },\n" +
            "    \"MYR\": {\n" +
            "      \"code\": \"MYR\",\n" +
            "      \"value\": 4.5818605798\n" +
            "    },\n" +
            "    \"MZN\": {\n" +
            "      \"code\": \"MZN\",\n" +
            "      \"value\": 63.6921400105\n" +
            "    },\n" +
            "    \"NAD\": {\n" +
            "      \"code\": \"NAD\",\n" +
            "      \"value\": 18.8712733062\n" +
            "    },\n" +
            "    \"NGN\": {\n" +
            "      \"code\": \"NGN\",\n" +
            "      \"value\": 762.8205791873\n" +
            "    },\n" +
            "    \"NIO\": {\n" +
            "      \"code\": \"NIO\",\n" +
            "      \"value\": 36.4992893815\n" +
            "    },\n" +
            "    \"NOK\": {\n" +
            "      \"code\": \"NOK\",\n" +
            "      \"value\": 10.2670012052\n" +
            "    },\n" +
            "    \"NPR\": {\n" +
            "      \"code\": \"NPR\",\n" +
            "      \"value\": 132.2123572631\n" +
            "    },\n" +
            "    \"NZD\": {\n" +
            "      \"code\": \"NZD\",\n" +
            "      \"value\": 1.6509102122\n" +
            "    },\n" +
            "    \"OMR\": {\n" +
            "      \"code\": \"OMR\",\n" +
            "      \"value\": 0.3841600547\n" +
            "    },\n" +
            "    \"OP\": {\n" +
            "      \"code\": \"OP\",\n" +
            "      \"value\": 0.5940270163\n" +
            "    },\n" +
            "    \"PAB\": {\n" +
            "      \"code\": \"PAB\",\n" +
            "      \"value\": 0.9989701223\n" +
            "    },\n" +
            "    \"PEN\": {\n" +
            "      \"code\": \"PEN\",\n" +
            "      \"value\": 3.6874606026\n" +
            "    },\n" +
            "    \"PGK\": {\n" +
            "      \"code\": \"PGK\",\n" +
            "      \"value\": 3.4898104649\n" +
            "    },\n" +
            "    \"PHP\": {\n" +
            "      \"code\": \"PHP\",\n" +
            "      \"value\": 56.3489261704\n" +
            "    },\n" +
            "    \"PKR\": {\n" +
            "      \"code\": \"PKR\",\n" +
            "      \"value\": 287.7818789095\n" +
            "    },\n" +
            "    \"PLN\": {\n" +
            "      \"code\": \"PLN\",\n" +
            "      \"value\": 4.0707106573\n" +
            "    },\n" +
            "    \"PYG\": {\n" +
            "      \"code\": \"PYG\",\n" +
            "      \"value\": 7291.4612531165\n" +
            "    },\n" +
            "    \"QAR\": {\n" +
            "      \"code\": \"QAR\",\n" +
            "      \"value\": 3.6399903916\n" +
            "    },\n" +
            "    \"RON\": {\n" +
            "      \"code\": \"RON\",\n" +
            "      \"value\": 4.5151108019\n" +
            "    },\n" +
            "    \"RSD\": {\n" +
            "      \"code\": \"RSD\",\n" +
            "      \"value\": 106.5344708312\n" +
            "    },\n" +
            "    \"RUB\": {\n" +
            "      \"code\": \"RUB\",\n" +
            "      \"value\": 97.1974028411\n" +
            "    },\n" +
            "    \"RWF\": {\n" +
            "      \"code\": \"RWF\",\n" +
            "      \"value\": 1178.5477703608\n" +
            "    },\n" +
            "    \"SAR\": {\n" +
            "      \"code\": \"SAR\",\n" +
            "      \"value\": 3.7466506978\n" +
            "    },\n" +
            "    \"SBD\": {\n" +
            "      \"code\": \"SBD\",\n" +
            "      \"value\": 8.3951530036\n" +
            "    },\n" +
            "    \"SCR\": {\n" +
            "      \"code\": \"SCR\",\n" +
            "      \"value\": 14.2579923773\n" +
            "    },\n" +
            "    \"SDG\": {\n" +
            "      \"code\": \"SDG\",\n" +
            "      \"value\": 601.5\n" +
            "    },\n" +
            "    \"SEK\": {\n" +
            "      \"code\": \"SEK\",\n" +
            "      \"value\": 10.7184319546\n" +
            "    },\n" +
            "    \"SGD\": {\n" +
            "      \"code\": \"SGD\",\n" +
            "      \"value\": 1.3464501693\n" +
            "    },\n" +
            "    \"SHP\": {\n" +
            "      \"code\": \"SHP\",\n" +
            "      \"value\": 0.7848201127\n" +
            "    },\n" +
            "    \"SLL\": {\n" +
            "      \"code\": \"SLL\",\n" +
            "      \"value\": 21152.754061343\n" +
            "    },\n" +
            "    \"SOL\": {\n" +
            "      \"code\": \"SOL\",\n" +
            "      \"value\": 0.0412185537\n" +
            "    },\n" +
            "    \"SOS\": {\n" +
            "      \"code\": \"SOS\",\n" +
            "      \"value\": 569.5590870729\n" +
            "    },\n" +
            "    \"SRD\": {\n" +
            "      \"code\": \"SRD\",\n" +
            "      \"value\": 38.4315348319\n" +
            "    },\n" +
            "    \"STD\": {\n" +
            "      \"code\": \"STD\",\n" +
            "      \"value\": 22366.539579205\n" +
            "    },\n" +
            "    \"SVC\": {\n" +
            "      \"code\": \"SVC\",\n" +
            "      \"value\": 8.75\n" +
            "    },\n" +
            "    \"SYP\": {\n" +
            "      \"code\": \"SYP\",\n" +
            "      \"value\": 13100.024875497\n" +
            "    },\n" +
            "    \"SZL\": {\n" +
            "      \"code\": \"SZL\",\n" +
            "      \"value\": 18.9050121485\n" +
            "    },\n" +
            "    \"THB\": {\n" +
            "      \"code\": \"THB\",\n" +
            "      \"value\": 35.0194267817\n" +
            "    },\n" +
            "    \"TJS\": {\n" +
            "      \"code\": \"TJS\",\n" +
            "      \"value\": 10.9436721615\n" +
            "    },\n" +
            "    \"TMT\": {\n" +
            "      \"code\": \"TMT\",\n" +
            "      \"value\": 3.5\n" +
            "    },\n" +
            "    \"TND\": {\n" +
            "      \"code\": \"TND\",\n" +
            "      \"value\": 3.0745004\n" +
            "    },\n" +
            "    \"TOP\": {\n" +
            "      \"code\": \"TOP\",\n" +
            "      \"value\": 2.3445903434\n" +
            "    },\n" +
            "    \"TRY\": {\n" +
            "      \"code\": \"TRY\",\n" +
            "      \"value\": 26.9544952934\n" +
            "    },\n" +
            "    \"TTD\": {\n" +
            "      \"code\": \"TTD\",\n" +
            "      \"value\": 6.7090010732\n" +
            "    },\n" +
            "    \"TWD\": {\n" +
            "      \"code\": \"TWD\",\n" +
            "      \"value\": 31.8325753771\n" +
            "    },\n" +
            "    \"TZS\": {\n" +
            "      \"code\": \"TZS\",\n" +
            "      \"value\": 2472.7080908797\n" +
            "    },\n" +
            "    \"UAH\": {\n" +
            "      \"code\": \"UAH\",\n" +
            "      \"value\": 36.6855371447\n" +
            "    },\n" +
            "    \"UGX\": {\n" +
            "      \"code\": \"UGX\",\n" +
            "      \"value\": 3617.5791293887\n" +
            "    },\n" +
            "    \"USD\": {\n" +
            "      \"code\": \"USD\",\n" +
            "      \"value\": 1\n" +
            "    },\n" +
            "    \"USDC\": {\n" +
            "      \"code\": \"USDC\",\n" +
            "      \"value\": 0.9984950904\n" +
            "    },\n" +
            "    \"USDT\": {\n" +
            "      \"code\": \"USDT\",\n" +
            "      \"value\": 0.9987407427\n" +
            "    },\n" +
            "    \"UYU\": {\n" +
            "      \"code\": \"UYU\",\n" +
            "      \"value\": 38.1004164352\n" +
            "    },\n" +
            "    \"UZS\": {\n" +
            "      \"code\": \"UZS\",\n" +
            "      \"value\": 11626.864987575\n" +
            "    },\n" +
            "    \"VEF\": {\n" +
            "      \"code\": \"VEF\",\n" +
            "      \"value\": 3105575.0145967\n" +
            "    },\n" +
            "    \"VND\": {\n" +
            "      \"code\": \"VND\",\n" +
            "      \"value\": 23725.800609556\n" +
            "    },\n" +
            "    \"VUV\": {\n" +
            "      \"code\": \"VUV\",\n" +
            "      \"value\": 119.9021985076\n" +
            "    },\n" +
            "    \"WST\": {\n" +
            "      \"code\": \"WST\",\n" +
            "      \"value\": 2.7506136331\n" +
            "    },\n" +
            "    \"XAF\": {\n" +
            "      \"code\": \"XAF\",\n" +
            "      \"value\": 598.4539950278\n" +
            "    },\n" +
            "    \"XAG\": {\n" +
            "      \"code\": \"XAG\",\n" +
            "      \"value\": 0.0439350243\n" +
            "    },\n" +
            "    \"XAU\": {\n" +
            "      \"code\": \"XAU\",\n" +
            "      \"value\": 0.0005193365\n" +
            "    },\n" +
            "    \"XCD\": {\n" +
            "      \"code\": \"XCD\",\n" +
            "      \"value\": 2.7\n" +
            "    },\n" +
            "    \"XDR\": {\n" +
            "      \"code\": \"XDR\",\n" +
            "      \"value\": 0.7467601475\n" +
            "    },\n" +
            "    \"XOF\": {\n" +
            "      \"code\": \"XOF\",\n" +
            "      \"value\": 598.4540151973\n" +
            "    },\n" +
            "    \"XPD\": {\n" +
            "      \"code\": \"XPD\",\n" +
            "      \"value\": 0.0008146122\n" +
            "    },\n" +
            "    \"XPF\": {\n" +
            "      \"code\": \"XPF\",\n" +
            "      \"value\": 108.8002602576\n" +
            "    },\n" +
            "    \"XPT\": {\n" +
            "      \"code\": \"XPT\",\n" +
            "      \"value\": 0.0011068814\n" +
            "    },\n" +
            "    \"XRP\": {\n" +
            "      \"code\": \"XRP\",\n" +
            "      \"value\": 1.5513959612\n" +
            "    },\n" +
            "    \"YER\": {\n" +
            "      \"code\": \"YER\",\n" +
            "      \"value\": 249.8097130174\n" +
            "    },\n" +
            "    \"ZAR\": {\n" +
            "      \"code\": \"ZAR\",\n" +
            "      \"value\": 18.9171818975\n" +
            "    },\n" +
            "    \"ZMK\": {\n" +
            "      \"code\": \"ZMK\",\n" +
            "      \"value\": 9001.2\n" +
            "    },\n" +
            "    \"ZMW\": {\n" +
            "      \"code\": \"ZMW\",\n" +
            "      \"value\": 19.3396533137\n" +
            "    },\n" +
            "    \"ZWL\": {\n" +
            "      \"code\": \"ZWL\",\n" +
            "      \"value\": 4761.7819568377\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public void mainScheduled(){
        Request request = new Request();
        try {
            request = ExecuteApi();
            request.setResponse(json);
            currency.SaveRequest(request);
            currency.SaveCurrencies(request);
        }
        catch (ParseException | JsonProcessingException ie){
            Thread.currentThread().interrupt();
        }
    }

    @GetMapping
    @RequestMapping(value = "{cur}", method = RequestMethod.GET)
    public ResponseEntity<?> CurrencyNormal(@PathVariable String cur,
                                      @RequestParam(name="finit",required=false,defaultValue = "") String finit,
                                      @RequestParam(name="fend",required=false,defaultValue = "") String fend){
        List<Currency> list;

        try {
            if (!finit.isEmpty())LocalDateTime.parse(finit);
            if (!fend.isEmpty())LocalDateTime.parse(fend);
        }catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Parameters");
        }

        if(Objects.equals(cur.toUpperCase(), "ALL"))
        {
            list = currency.GetAllCurrencies();
        }
        else {
            list = currency.SearchCurrencyByCodeAndDate(cur,finit,fend);
        }

        if(!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        else {
            if(Objects.equals(cur.toUpperCase(), "ALL")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found currencies in Database");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency "+ cur +" not found");
            }
        }
    }

    private Request ExecuteApi(){
        Request req= new Request();
        long startTime=0;
        try {
            //String urlCurrencyApi = uri + apikey;
            String urlCurrencyApi = "https://catfact.ninja/fact?max_length=5000";
            req.setUrl(urlCurrencyApi);
            ObjectMapper mapper = new ObjectMapper();

            startTime = System.currentTimeMillis();
            req.setRequestDateTime(LocalDateTime.now());
            Object objResponse = restTemplate.getForObject(urlCurrencyApi, Object.class);
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = mapper.writeValueAsString(objResponse);
            req.status = 200;
        }
        catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc){
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = httpClientOrServerExc.getMessage();
            req.status = httpClientOrServerExc.getStatusCode().value();
        }
        catch (JsonProcessingException ex) {
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = ex.getMessage();
            req.status = 503;
        }
        catch (ResourceAccessException accessException){
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = accessException.getMessage();
            req.status = 408;
        }

        return req;
    }
}
