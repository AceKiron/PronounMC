package dev.mxace.pronounmc.api;

import com.google.common.reflect.ClassPath;

import dev.mxace.pronounmc.PronounMC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PronounAPI {
    public final static PronounAPI instance = new PronounAPI();
    private PronounAPI() {

    }

    private List<PronounsEventListener> m_Listeners = new ArrayList<PronounsEventListener>();
    public List<PronounsEventListener> getListeners() { return m_Listeners; }

    private List<PronounsSet> m_RegisteredPronouns = new ArrayList<PronounsSet>();
    public List<PronounsSet> getRegisteredPronouns() {
        return m_RegisteredPronouns;
    }

    public void addListener(PronounsEventListener listener) {
        m_Listeners.add(listener);
    }

    public void loadPronounsSetsInPackage(String packageName) throws ClassNotFoundException, IOException {
        ClassPath path = ClassPath.from(PronounMC.instance.classLoader);
        for (ClassPath.ClassInfo info : path.getTopLevelClasses(packageName)) Class.forName(info.getName(), true, PronounMC.instance.classLoader);
    }

    public void registerPronounsSet(PronounsSet pronounsSet) {
        assert !m_RegisteredPronouns.contains(pronounsSet);
        m_RegisteredPronouns.add(pronounsSet);

        for (PronounsEventListener listener : m_Listeners) listener.onPronounsSetRegistered(pronounsSet);
    }

    public void unregisterPronounsSet(PronounsSet pronounsSet) {
        assert m_RegisteredPronouns.contains(pronounsSet);
        m_RegisteredPronouns.remove(pronounsSet);

        for (PronounsEventListener listener : m_Listeners) listener.onPronounsSetUnregistered(pronounsSet);
    }

    public String approvementStatusToString(PronounsSetApprovementStatus approvementStatus) {
        switch (approvementStatus) {
            case ASK:
                return "Ask";
            case YES:
                return "Yes";
            case JOKINGLY:
                return "Jokingly";
            case ONLY_IF_WE_ARE_CLOSE:
                return "Only if we are close";
            case OKAY:
                return "Okay";
            case NOPE:
                return "Nope";

            default:
                return "Unknown";
        }
    }
}
