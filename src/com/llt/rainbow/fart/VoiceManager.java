package com.llt.rainbow.fart;

import com.alibaba.fastjson.JSON;
import com.llt.rainbow.fart.model.ConfigModel;
import com.llt.rainbow.fart.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author llt11
 */
@SuppressWarnings("AlibabaAvoidUseTimer")
public class VoiceManager {

    private static long start = System.currentTimeMillis();

    private static Timer timer = new Timer();

    private static Map<String, List<byte[]>> SOUNDS_MAP;

    private static List<Character> characters = new LinkedList<>();

    private static ConfigModel configModel;

    private static String LONG_TIME_KEY = "longTimes";

    static {
        SOUNDS_MAP = new HashMap<>();
        try {
            ClassLoader classLoader = VoicePlayer.class.getClassLoader();
            String s = IOUtils.toString(classLoader.getResource("built-in-voice-chinese/contributes.json"), "UTF-8");
            configModel = JSON.parseObject(s, ConfigModel.class);
            List<ConfigModel.Contribute> contributes = configModel.getContributes();
            List<ConfigModel.Contribute> timers = configModel.getTimers();
            contributes.addAll(timers);
            List<String> longTimes = configModel.getLongTimes();

            ConfigModel.Contribute longTimeContribute = new ConfigModel.Contribute();
            longTimeContribute.setVoices(longTimes);
            longTimeContribute.setKeywords(Collections.singletonList(LONG_TIME_KEY));
            contributes.add(longTimeContribute);

            contributes.forEach(contribute -> {
                List<String> voices = contribute.getVoices();
                List<byte[]> collect = voices.stream().map(v -> {
                    try {
                        return IOUtils.toByteArray(classLoader.getResourceAsStream("/built-in-voice-chinese/" + v));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                List<String> keywords = contribute.getKeywords();
                keywords.forEach(key -> SOUNDS_MAP.put(key, collect));
            });
            LocalTime nowTime = LocalTime.now();
            LocalDate nowDate = LocalDate.now();
            LocalDate tomorrowDate = nowDate.plusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            for (ConfigModel.Contribute contribute : timers) {
                List<String> keywords = contribute.getKeywords();
                for (String timeStr : keywords) {
                    LocalTime time = LocalTime.parse(timeStr, formatter);
                    LocalDateTime dateTime;
                    if (time.isBefore(nowTime)) {
                        dateTime = tomorrowDate.atTime(time);
                    } else {
                        dateTime = nowDate.atTime(time);
                    }
                    Date date = DateUtil.localDateTime2Date(dateTime);
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    List<byte[]> bytes = SOUNDS_MAP.get(timeStr);
                                    if (!CollectionUtils.isEmpty(bytes)) {
                                        VoicePlayer.randomPlay(bytes);
                                    }
                                }
                            }, date);
                }

            }
            List<byte[]> bytes = SOUNDS_MAP.get(LONG_TIME_KEY);
            if (!CollectionUtils.isEmpty(bytes)) {
                for (int i = 1; i <50 ; i++) {
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    VoicePlayer.randomPlay(bytes);
                                }
                            }, 45*60*1000*i);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void addChar(char c) {
        int size = characters.size();
        if (characters.size() > 5) {
            characters.remove(0);
        }
        characters.add(c);
        if (size == 5) {
            size = 6;
        }
        List<String> wordList = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            StringBuilder w = new StringBuilder();
            for (int j = size - i; j < size; j++) {
                w.append(characters.get(j));
            }
            wordList.add(w.toString());
        }
        for (String word : wordList) {
            //System.out.println(word);
            List<byte[]> bytes = SOUNDS_MAP.get(word);
            if (!CollectionUtils.isEmpty(bytes)) {
                VoicePlayer.randomPlay(bytes);
                break;
            }
        }
    }

}
