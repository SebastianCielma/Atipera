package pl.sebastiancielma.Atipera.model;

public record RepositoryInfo(String name, boolean fork, OwnerInfo ownerInfo, java.util.List<BranchInfo> branches) {}
