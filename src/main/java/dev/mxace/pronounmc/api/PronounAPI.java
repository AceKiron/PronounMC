package dev.mxace.pronounmc.api;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import dev.mxace.pronounmc.PronounMC;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main API class.
 * @author AceKiron
 * @version 2.3
 */
public class PronounAPI {
    /**
     * Singleton instance of the PronounAPI class.
     */
    public final static PronounAPI instance = new PronounAPI();

    /**
     * Make constructor private.
     */
    private PronounAPI() {

    }

    /**
     * List of event listeners for PronounMC's events.
     * @see java.util.List
     * @see dev.mxace.pronounmc.api.PronounsEventListener
     */
    private final List<PronounsEventListener> m_Listeners = new ArrayList<PronounsEventListener>();

    /**
     * Return list of event listeners for PronounMC's events.
     * @return List of event listeners for PronounMC's events.
     * @see java.util.List
     * @see dev.mxace.pronounmc.api.PronounsEventListener
     */
    public List<PronounsEventListener> getListeners() { return m_Listeners; }

    /**
     * List of registered pronouns sets.
     * @see java.util.List
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    private final List<PronounsSet> m_RegisteredPronouns = new ArrayList<PronounsSet>();

    /**
     * Return list of registered pronouns sets.
     * @return List of registered pronouns sets.
     * @see java.util.List
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public List<PronounsSet> getRegisteredPronouns() {
        return m_RegisteredPronouns;
    }

    /**
     * Adds an event listener to the list.
     * @param listener Event listener that listens for PronounMC's events.
     * @see dev.mxace.pronounmc.api.PronounsEventListener
     */
    public void addListener(@NotNull PronounsEventListener listener) {
        m_Listeners.add(listener);
    }

    /**
     * Loads all pronouns sets in a specified package.
     * @param classLoader Typically this.getClass().getClassLoader().
     * @param packageName The package where the pronouns sets are located.
     * @throws ClassNotFoundException Gets thrown if the pronouns sets could not be loaded.
     * @throws IOException Gets thrown if the pronouns sets could not be loaded.
     * @see ClassLoader
     */
    @SuppressWarnings("UnstableApiUsage")
    public void loadPronounsSetsInPackage(@NotNull ClassLoader classLoader, @NotNull String packageName) throws ClassNotFoundException, IOException {
        ClassPath path = ClassPath.from(classLoader);
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = path.getTopLevelClasses(packageName);
        for (ClassPath.ClassInfo classInfo : topLevelClasses) Class.forName(classInfo.getName(), true, classLoader);
        System.out.println("Loaded " + topLevelClasses.size() + " pronouns set(s).");
    }

    /**
     * Adds a pronouns set to the list and fires an event to all listeners.
     * @param pronounsSet Pronouns set to be added to the list.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public void registerPronounsSet(@NotNull PronounsSet pronounsSet) {
        assert !m_RegisteredPronouns.contains(pronounsSet);
        m_RegisteredPronouns.add(pronounsSet);

        for (PronounsEventListener listener : m_Listeners) listener.onPronounsSetRegistered(pronounsSet);
    }

    /**
     * Removes a pronouns set from the list and fires an event to all listeners.
     * @param pronounsSet Pronouns set to be removed from the list.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public void unregisterPronounsSet(@NotNull PronounsSet pronounsSet) {
        assert m_RegisteredPronouns.contains(pronounsSet);
        m_RegisteredPronouns.remove(pronounsSet);

        for (PronounsEventListener listener : m_Listeners) listener.onPronounsSetUnregistered(pronounsSet);
    }

    /**
     * Converts a pronouns set approvement status to String.
     * @param approvementStatus Pronouns set approvement status as PronounsSetApprovementStatus.
     * @return Pronouns set approvement status as String.
     * @see dev.mxace.pronounmc.api.PronounsSetApprovementStatus
     */
    public String approvementStatusToString(@NotNull PronounsSetApprovementStatus approvementStatus) {
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
